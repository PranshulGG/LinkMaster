const edit_link_dialog = document.getElementById('edit_link_dialog');
const edit_link_name_input = document.getElementById('edit_link_name_input');
const edit_link_address_input = document.getElementById('edit_link_address_input');
const edit_link_note_input = document.getElementById('edit_link_note_input');
const saveEditedLinkBtn = document.getElementById('saveEditedLinkBtn');
const UseShowConfirmationDialog = localStorage.getItem('UseShowConfirmationDialog');

const linkSearchBar = document.getElementById('linkSearchBar');

linkSearchBar.addEventListener('input', searchLinks);

const folderSearchBar = document.getElementById('folderSearchBar');

folderSearchBar.addEventListener('input', searchFolders);

function searchLinks() {
    const searchTerm = linkSearchBar.value.toLowerCase();
    const folders = JSON.parse(localStorage.getItem('folders')) || [];
    saved_links.innerHTML = ""; 

    folders.forEach(folder => {
        if (folder.links.length > 0) {
            folder.links.forEach(linkData => {
                if (linkData.headline.toLowerCase().includes(searchTerm)) {
                    renderLink(linkData, folder.name);
                }
            });
        }
    });

    if (saved_links.children.length === 0) {
        document.getElementById('home_page_watermark').hidden = false;
    } else {
        document.getElementById('home_page_watermark').hidden = true;
    }
}

function searchFolders() {
    const searchTerm = folderSearchBar.value.toLowerCase();
    let folders = JSON.parse(localStorage.getItem('folders')) || [];
    folders_container.innerHTML = ""; 

    folders.forEach(folderData => {
        if (folderData.name.toLowerCase().includes(searchTerm)) {
            renderFolder(folderData);
        }
    });

    if (folders_container.children.length === 0) {
        const noFoldersMessage = document.createElement('div');
        noFoldersMessage.textContent = "No folders found";
        folders_container.appendChild(noFoldersMessage);
    }
}

// create a link

const link_folder_menu = document.getElementById('link_folder_menu');
const link_name_input = document.getElementById('link_name_input');
const link_address_input = document.getElementById('link_address_input');
const link_note_input = document.getElementById('link_note_input');
const save_folder_link_btn = document.getElementById('save_folder_link_btn');
const folder_filter_set = document.getElementById('folder_filter_set');
const openIncognitoLinkSwitch = document.getElementById('openIncognitoLinkSwitch');

save_folder_link_btn.addEventListener('click', createlink);

function checkInputFields() {
    const linkNameValue = link_name_input.value.trim();
    const linkAddressValue = link_address_input.value.trim();
    const folderSelected = link_folder_menu.value;

    if (linkNameValue.length === 0 || linkAddressValue.length === 0 || folderSelected === "") {
        save_folder_link_btn.disabled = true;
    } else {
        save_folder_link_btn.disabled = false;
    }
}

link_name_input.addEventListener('input', checkInputFields);
link_address_input.addEventListener('input', checkInputFields);
link_folder_menu.addEventListener('change', checkInputFields);

function createlink() {
    const saved_links = document.getElementById('saved_links');
    const folderSelected = link_folder_menu.value; 

    const linkHeadline = link_name_input.value;
    const linkAddress = link_address_input.value;
    const linkNote = link_note_input.value;

    

    let openIncognito
    
    if(openIncognitoLinkSwitch.selected){
        openIncognito = true;
    } else{
        openIncognito = false;
    }

    const linkData = {
        id: Date.now(), 
        headline: linkHeadline,
        address: linkAddress,
        note: linkNote,
        Incognito: openIncognito
    };
    

    let folders = JSON.parse(localStorage.getItem('folders')) || [];
    let selectedFolder = folders.find(folder => folder.name === folderSelected);

    if (selectedFolder) {
        selectedFolder.links = selectedFolder.links || [];
        selectedFolder.links.push(linkData);
        localStorage.setItem('folders', JSON.stringify(folders));
    }

    setTimeout(()=>{
        link_name_input.value = ''
        link_address_input.value = ''
        link_note_input.value = ''
        save_folder_link_btn.disabled = true;
        Toast('Link saved!', 'short')
    loadFolders();
    renderAllLinks(); 
    checkFirstChip()
    }, 150);


}

function renderLink(linkData, folderName) {
    const saved_links = document.getElementById('saved_links');

    const md_list_item = document.createElement('md-list-item');

    
 if(linkData.Incognito === true){
    md_list_item.setAttribute('IncognitoLink', 'true');
 } else{
    md_list_item.setAttribute('IncognitoLink', 'false');
   
 }


    md_list_item.innerHTML = `
    <click_area onclick="window.location.href='${linkData.address}'"><md-ripple></md-ripple></click_area>
        <div slot="start" class="folder_icon_holder">
            <md-icon icon-outlined>link</md-icon>
        </div>
        <div slot="headline">${linkData.headline}</div>
        <div slot="supporting-text">${linkData.address}</div>
        <div slot="supporting-text">${linkData.note}</div>
         <div slot="end">
        <md-icon-button class="copy-link-btn">
            <md-icon icon-outlined>content_copy</md-icon>
        </md-icon-button>
        <md-icon-button class="edit-link-btn">
            <md-icon icon-outlined>edit</md-icon>
        </md-icon-button>
        </div>
    `;
    md_list_item.querySelector('.edit-link-btn').addEventListener('click', () => editLink(linkData));
    md_list_item.querySelector('.copy-link-btn').addEventListener('click', () => copyLink(linkData.address));

    saved_links.appendChild(md_list_item);
}

function loadLinks(folderName) {
    saved_links.innerHTML = "";
    const folders = JSON.parse(localStorage.getItem('folders')) || [];
    let isFolderEmpty = true;
    folders.forEach(folder => {
        if (!folderName || folder.name === folderName) {
            if (folder.links.length === 0) {
                document.getElementById('home_page_watermark').hidden = false;
            } else {
                folder.links.forEach(linkData => {
                    renderLink(linkData, folder.name);
                  document.getElementById('home_page_watermark').hidden = true;
                    isFolderEmpty = false; 
                });
            }
        }
    });

}



// -------------------------------------------------

const create_new_folder_btn = document.getElementById('create_new_folder_btn');
const create_folder_dialog = document.getElementById('create_folder_dialog');
const folder_name_input = document.getElementById('folder_name_input');
const saveFolderBtn = document.getElementById('saveFolderBtn');

const edit_folder_dialog = document.getElementById('edit_folder_dialog');
const edit_folder_name_input = document.getElementById('edit_folder_name_input');
const saveEditedFolderBtn = document.getElementById('saveEditedFolderBtn');


saveFolderBtn.addEventListener('click', createFolder);

saveEditedFolderBtn.addEventListener('click', saveEditedFolder);

let folderBeingEdited = null;


function checkInputFieldFolder() {
    const FolderNameinputCheck = folder_name_input.value.trim();

    if (FolderNameinputCheck.length === 0) {
        saveFolderBtn.disabled = true;
    } else {
        saveFolderBtn.disabled = false;
    }
}

folder_name_input.addEventListener('input', checkInputFieldFolder);

create_new_folder_btn.addEventListener('click', () => {
    create_folder_dialog.show();
    window.history.pushState({ CreateFolderDialog: true }, "");
});

window.addEventListener('popstate', function (event) {
    if (create_folder_dialog.open) {
        create_folder_dialog.close();
    }
});

create_folder_dialog.addEventListener('closed', () => {
    setTimeout(() => {
        window.history.back();
        folder_name_input.value = '';
        folder_name_input.dispatchEvent(new Event('input'));
    }, 250);
    
});


function createFolder() {
    const folderName = folder_name_input.value;

    let folders = JSON.parse(localStorage.getItem('folders')) || [];

    folders.push({ name: folderName, links: [] });

    localStorage.setItem('folders', JSON.stringify(folders));



    renderFolder({ name: folderName, links: [] });
    updateFolderMenu();
    updateFolderFilter();
    updateChips()
    checkFirstChip()
    window.history.back();
}


function renderFolder(folderData) {
    const folders_container = document.getElementById('folders_container');


    const folder_item = document.createElement('md-list-item');
    folder_item.innerHTML = `
        <div slot="start" class="folder_icon_holder">
            <md-icon icon-outlined>folder_open</md-icon>
        </div>
        <div slot="headline">${folderData.name}</div>
        <div slot="supporting-text">${folderData.links.length} links</div>
        <div slot="end">
        <md-icon-button  class="edit-folder-btn">
            <md-icon icon-outlined>edit</md-icon>
        </md-icon-button>
        <md-icon-button class="delete-folder-btn">
            <md-icon icon-outlined>delete</md-icon>
        </md-icon-button></div>
    `;
//cool

    folders_container.appendChild(folder_item);
    




    folder_item.querySelector('.edit-folder-btn').addEventListener('click', () => editFolder(folderData));

    folder_item.querySelector('.delete-folder-btn').addEventListener('click', ()=>{
        if(UseShowConfirmationDialog === 'true'){
            if (confirm("Are you sure you want to proceed?")) {
                deleteFolder(folderData.name)
            }
        } else{
            deleteFolder(folderData.name)
        }
    });

}

function editFolder(folderData) {
    folderBeingEdited = folderData;
    edit_folder_name_input.value = folderData.name; 
    edit_folder_dialog.show();
}

function saveEditedFolder() {
    const newFolderName = edit_folder_name_input.value.trim();
    
    if (newFolderName && folderBeingEdited) {
        let folders = JSON.parse(localStorage.getItem('folders')) || [];
        const folderIndex = folders.findIndex(folder => folder.name === folderBeingEdited.name);
        
        if (folderIndex !== -1) {
            folders[folderIndex].name = newFolderName;
            localStorage.setItem('folders', JSON.stringify(folders));
            
            loadFolders(); 
            updateFolderMenu(); 
            updateFolderFilter();
            updateChips()
        }
        edit_folder_dialog.close();
    }
}


function deleteFolder(folderName) {
    let folders = JSON.parse(localStorage.getItem('folders')) || [];
    folders = folders.filter(folder => folder.name !== folderName);
    localStorage.setItem('folders', JSON.stringify(folders));

    loadFolders();
    updateFolderMenu(); 
    updateFolderFilter(); 
    renderAllLinks(); 
}

function updateFolderMenu() {
    const folders = JSON.parse(localStorage.getItem('folders')) || [];
    link_folder_menu.innerHTML = `
  
    
    `;
    folders.forEach(folder => {
        const option = document.createElement('md-select-option');
        const optionDiv = document.createElement('div');

        option.setAttribute('menu-type', '')

        option.value = folder.name;
        optionDiv.textContent = folder.name;

        link_folder_menu.appendChild(option);
        option.appendChild(optionDiv)


    });
}

function updateFolderFilter() {
    const folders = JSON.parse(localStorage.getItem('folders')) || [];
    folder_filter_set.innerHTML = ''; 
    

    folders.forEach(folder => {
        const chipFolder = document.createElement('md-filter-chip');

        chipFolder.setAttribute('label', folder.name)
        chipFolder.setAttribute('value', folder.name)





        chipFolder.addEventListener('click', () => {
            loadLinks(folder.name);
        });


        folder_filter_set.appendChild(chipFolder)


        setTimeout(()=>{
            if (folders.length > 0) {
                link_folder_menu.value = folders[0].name;

            }
        }, 200);
    });



}

function loadFolders() {
    let folders = JSON.parse(localStorage.getItem('folders')) || [];
    
    folders_container.innerHTML = ''


    if (folders.length === 0) {
        const defaultFolder = { name: 'New folder', links: [] };
        folders.push(defaultFolder);
        localStorage.setItem('folders', JSON.stringify(folders));

    } else{
    }

    folders.forEach(folderData => {
        renderFolder(folderData);
    });



    updateFolderMenu();
    updateFolderFilter();
}

loadFolders();


function updateChips(){
const chipSetFull = document.getElementById('folder_filter_set');
  const chipsFull = chipSetFull.querySelectorAll('md-filter-chip');



  chipsFull.forEach(chip => {
    chip.addEventListener('click', () => {
        chipsFull.forEach(ch => ch.selected = false);
      chip.selected = true;
    });
  });
}

function checkFirstChip(){
const chipSetFullMain = document.getElementById('folder_filter_set');
  const chipsFullMain = chipSetFullMain.querySelectorAll('md-filter-chip');

if (chipsFullMain.length > 0) {
    chipsFullMain[0].click()
    chipsFullMain[0].selected = true;

}
}

//   setTimeout(()=>{
//     sendThemeToAndroid('YellowTabsDark');
//   }, 300);

// ---------------------------------------------------------------------


const manageLinksContainer = document.getElementById('manage_links');
const deleteSelectedLinksBtn = document.getElementById('delete_selected_links_btn');
const selectedCountDisplay = document.getElementById('selected_count');


function renderAllLinks() {
    manageLinksContainer.innerHTML = "";
    const folders = JSON.parse(localStorage.getItem('folders')) || [];

    let totalLinks = 0;
    
    folders.forEach(folder => {
        folder.links.forEach(linkData => {
            totalLinks++;
            const md_list_item = document.createElement('md-list-item');
            md_list_item.setAttribute('type', 'button');

 
            const checkbox = document.createElement('input');
            checkbox.type = 'checkbox';
            checkbox.value = linkData.id; 
            checkbox.id = linkData.id

            md_list_item.innerHTML = `
            <label for="${linkData.id}">hello</label>
                <div slot="start" class="folder_icon_holder">
                    <md-icon icon-outlined>link</md-icon>
                </div>
                <div slot="headline">${linkData.headline}</div>
                <div slot="supporting-text">${linkData.address}</div>
                <div slot="supporting-text">${folder.name}</div>

            `;

            md_list_item.prepend(checkbox);

            checkbox.addEventListener('change', function() {
                if (this.checked) {
                    md_list_item.classList.add('checked'); 
                } else {
                    md_list_item.classList.remove('checked');
                }
                updateSelectedCount(); 
            });
            manageLinksContainer.appendChild(md_list_item);
        });
    });

    updateSelectedCount();
}

function updateSelectedCount() {
    const checkboxes = manageLinksContainer.querySelectorAll('input[type="checkbox"]');
    const selectedCount = Array.from(checkboxes).filter(checkbox => checkbox.checked).length;
    const totalCount = checkboxes.length;

    selectedCountDisplay.textContent = `${selectedCount}/${totalCount} Selected`;

    if(selectedCount < 1){
        setTimeout(()=>{
            deleteSelectedLinksBtn.disabled = true;
        }, 300);
        document.getElementById('manage_page_watermark').hidden = false;
    } else{
        deleteSelectedLinksBtn.disabled = false;
        document.getElementById('manage_page_watermark').hidden = true;
    }
}

function deleteSelectedLinks() {

    const checkboxes = manageLinksContainer.querySelectorAll('input[type="checkbox"]');
    const selectedIds = Array.from(checkboxes)
        .filter(checkbox => checkbox.checked)
        .map(checkbox => checkbox.value);

    let folders = JSON.parse(localStorage.getItem('folders')) || [];
    
    if (selectedIds.length > 0) {
        folders.forEach(folder => {
            if (folder.links) {
                folder.links = folder.links.filter(link => {
                    return link.id && !selectedIds.includes(link.id.toString());
                });
            }
        });

        localStorage.setItem('folders', JSON.stringify(folders));
        renderAllLinks(); 
    } else {
        console.warn("No links selected for deletion.");
    }

    loadLinks()
    loadFolders();
}




deleteSelectedLinksBtn.addEventListener('click', ()=>{

    if(UseShowConfirmationDialog === 'true'){
        if (confirm("Are you sure you want to proceed?")) {
            deleteSelectedLinks()
        }
    } else{
        deleteSelectedLinks()
    }
});

renderAllLinks(); 


function openLinkRegular(link) {
    Android.openLinkRegular(link);
}

// ----------

const UseBiometricauthentication = localStorage.getItem('UseBiometricauthentication');
const isUnlocked = sessionStorage.getItem('Unlocked');
const cover = document.querySelector('.cover');



if(UseBiometricauthentication && UseBiometricauthentication === 'true'){
if(isUnlocked && isUnlocked === 'true'){
    cover.hidden = true;

} else{
    sendThemeToAndroid('ShowBiometric')
}
} else{
    cover.hidden = true;
}


function removeCover(){
    cover.hidden = true;
    sessionStorage.setItem('Unlocked', 'true');
}

function copyLink(data) {
    const textArea = document.createElement('textarea');
    
    textArea.style.position = 'absolute';
    textArea.style.left = '-9999px';
    textArea.setAttribute('inputmode', 'none')
    
    textArea.value = data;
    
    document.body.appendChild(textArea);
    
    textArea.select();
    textArea.setSelectionRange(0, 99999);
    
    try {
        document.execCommand('copy');
        console.log('Text copied to clipboard: ' + data);
        Toast('Text copied to clipboard', 'long')
    } catch (err) {
        console.error('Error copying text: ', err);
        Toast('Error copying text', 'long')
    }
    
    setTimeout(()=>{
        document.body.removeChild(textArea);
    }, 130);
}

updateChips()
checkFirstChip()
updateSelectedCount()