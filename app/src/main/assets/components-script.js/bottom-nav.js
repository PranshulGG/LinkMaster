// tabs-nav

const TabNameLabel = document.getElementById('tab_label')

let currentTab = parseInt(localStorage.getItem('currentTab')) || 0;
showTab(currentTab);
function showTab(tabIndex) {



    document.getElementById(`tab-content-${currentTab}`).style.display = 'none';



    const previousTab = document.querySelector('.tab.active');
    const previousname = document.querySelector('.label.active-name');

    if (previousTab) {
        previousTab.classList.remove('active');
        previousname.classList.remove('active-name');

    }



    document.getElementById(`tab-content-${tabIndex}`).style.display = 'block';

    document.querySelectorAll('.tab')[tabIndex].classList.add('active');
    document.querySelectorAll('.label')[tabIndex].classList.add('active-name');
    currentTab = tabIndex;

    const current_tab_label = document.getElementById('current_tab_label');
    const create_new_folder_btn = document.getElementById('create_new_folder_btn');

    if(tabIndex === 0){
        current_tab_label.innerHTML = 'Home';
        create_new_folder_btn.hidden = true;
    } else if (tabIndex === 1){
        current_tab_label.innerHTML = 'Folders';
        create_new_folder_btn.hidden = false;
    } else if (tabIndex === 2){
        current_tab_label.innerHTML = 'New link';
        create_new_folder_btn.hidden = true;
    } else if (tabIndex === 3){
        current_tab_label.innerHTML = 'Manage';
        create_new_folder_btn.hidden = true;
    }
    
    localStorage.setItem('currentTab', tabIndex);

}


document.querySelectorAll('.tab').forEach((tab, index) => {
    tab.addEventListener('click', () => showTab(index));
});

