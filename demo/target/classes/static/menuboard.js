function getLocationAndPost() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var lat = position.coords.latitude;
            var lng = position.coords.longitude;

            console.log("coords", lat, lng);
            var xhr = new XMLHttpRequest();
            var url = "/menuBoard";
            var data = JSON.stringify({ latitude: lat, longitude: lng });
            xhr.open("POST", url, true);
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.send(data);
        });
    }
}

getLocationAndPost();

function getCoordinates() {
        return {
            latitude: navigator.geolocation.coords.latitude,
            longitude: navigator.geolocation.coords.longitude
        };
    }

