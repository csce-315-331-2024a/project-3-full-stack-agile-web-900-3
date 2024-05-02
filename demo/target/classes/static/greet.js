function redirectToSelectedUrl() {
    console.log('redirecting to selected url');
    var selectedUrl = document.getElementById('actionDropdown').value;
    window.location.href = selectedUrl;
}