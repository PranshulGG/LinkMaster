
function editLink(linkData) {
    edit_link_name_input.value = linkData.headline;
    edit_link_address_input.value = linkData.address;
    edit_link_note_input.value = linkData.note;
    edit_link_dialog.show();
    ThemeDialogRegular()

    window.currentLinkId = linkData.id;
}

saveEditedLinkBtn.addEventListener('click', saveEditedLink);

function saveEditedLink() {
    const updatedLinkName = edit_link_name_input.value.trim();
    const updatedLinkAddress = edit_link_address_input.value.trim();
    const updatedLinkNote = edit_link_note_input.value.trim();

    let folders = JSON.parse(localStorage.getItem('folders')) || [];
    const linkId = window.currentLinkId;

    folders.forEach(folder => {
        folder.links.forEach(link => {
            if (link.id === linkId) {
                link.headline = updatedLinkName;
                link.address = updatedLinkAddress;
                link.note = updatedLinkNote;
            }
        });
    });

    localStorage.setItem('folders', JSON.stringify(folders));
    loadLinks();
    updateChips()
    edit_link_dialog.close();
}

edit_link_dialog.addEventListener('close', () => {
    ThemeTabs()
});