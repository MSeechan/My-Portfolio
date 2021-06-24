
$(document).ready(function () {
    $("#nav-bar").load("/nav.html")
});

async function showFetchTest() {
    const serverResponse = await fetch('/hello');

    const responseJson = await serverResponse.json();
    console.log(responseJson);

    const retArr = Object.values(responseJson);
    const randIndex = Math.floor(Math.random() * retArr.length);

    const factContainer = document.getElementById('fetch-container');
    factContainer.innerHTML = retArr[randIndex];
}

async function loadContacts() {
    // fetch from servlet
    const serverResponse = await fetch('/list-contacts');
    // server response returns Json list/obj
    const responseJson = await serverResponse.json();
    console.log(responseJson);
    //connects to html's container id
    const contactContainer = document.getElementById('message-list');
    // loops through responseJson foreach (takes only functions) contact element and appends to container
    responseJson.forEach(contact =>  
        contactContainer.appendChild(createContactElement(contact)));
      
}

function createContactElement(contact) {
    const contactElement = document.createElement('li');
    contactElement.className = 'contact';

    const titleElement = document.createElement('span');
    titleElement.innerHTML = "<strong>"+contact.name+"</strong> " + "<span style =\"color:lightgray; margin: 0 0 0 20px\">" +contact.timestamp + "</span>" + "<br />" + "<i>" + contact.message +"</i>";

    const deleteButtonElement = document.createElement('button');
    deleteButtonElement.className="btn";
    deleteButtonElement.innerText = 'Delete';
    deleteButtonElement.addEventListener('click', () => {
        deleteContact(contact);
        contactElement.remove();
    });

    contactElement.appendChild(titleElement);
    contactElement.appendChild(deleteButtonElement);
    return contactElement;
}

function deleteContact(contact) {
    const params = new URLSearchParams();
    params.append('id', contact.id);
    fetch('/delete-contact', { method: 'POST', body: params });
}


