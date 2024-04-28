const myModal = document.getElementById('myModal')
const myInput = document.getElementById('myInput')

myModal.addEventListener('shown.bs.modal', () => {
  myInput.focus()
})

function deleteUser(userId) {
    // Endpoint
    const url = '/admin/delete';

    // Fetch options
    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json', // Set to accept JSON
        },
        body: JSON.stringify({ user_id: userId }) // Sending data as JSON
    };

    // Send the POST request
    fetch(url, options)
        .then(response => response.json()) // Assuming the response is JSON
        .then(result => {
            console.log('Success:', result);
            // Optionally, you might want to refresh the page or part of the page
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function test(userID) {
    var xhr = new XMLHttpRequest();
                var url = "/admin/delete";
                var data = JSON.stringify({ user_id: userID});
                xhr.open("POST", url, true);
                xhr.setRequestHeader("Content-Type", "application/json");
                xhr.send(data);
}
