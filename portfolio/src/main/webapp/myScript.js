/** Fetches a random fact from the server and adds it to the page. */
async function showFetchTest() {
    const serverResponse = await fetch('/hello');
    const responseText = await serverResponse.text();

    const factContainer = document.getElementById('fetch-container');
    factContainer.innerText = responseText;
}