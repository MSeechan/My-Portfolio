/** Fetches a random fact from the server and adds it to the page. */
$(document).ready(function(){
    $("#nav-bar").load("/nav.html")});

async function showFetchTest() {
    const serverResponse = await fetch('/hello');
//server returns a json object
    const responseJson = await serverResponse.json();
    console.log(responseJson);
    //returns array of responseJson's values
    const retArr = Object.values(responseJson);
    const randIndex = Math.floor(Math.random() * retArr.length);

    const factContainer = document.getElementById('fetch-container');
    factContainer.innerHTML = retArr[randIndex];
}


