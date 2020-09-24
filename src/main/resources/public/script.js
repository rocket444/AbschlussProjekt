const URL = 'http://localhost:8080';
let entries = [];

const createUser = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const user = {};
    user['username'] = formData.get('name');
    user['email'] = formData.get('email');
    user['password'] = formData.get('password');

    fetch(`${URL}/users`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    }).then((result) => {
        result.json().then((user) => {
            entries.push(user);
        });
    });
};

const deleteEntry = (id) => {
    fetch(`${URL}/users/${id}`, {
        method: 'DELETE'
    }).then(() => {
        indexUsers();
    });
};

const updateEntry = (entry) => {
    fetch(`${URL}/users/${entry.id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(entry)
    }).then((result) => {
        result.json().then((entry) => {
            entries = entries.map((e) => e.id === entry.id ? entry : e);
            renderEntries();
        });
    });
}

const indexUsers = () => {
    fetch(`${URL}/users/`, {
        method: 'GET'
    }).then((result) => {
        result.json().then((result) => {
            entries = result;
            renderEntries();
        });
    });
    renderEntries();
};

const createActions = (user) => {
    const cell = document.createElement('td');

    const deleteButton = document.createElement('button');
    deleteButton.innerText = 'Delete';
    deleteButton.addEventListener('click', () => deleteEntry(user.id));
    cell.appendChild(deleteButton);

    const editButton = document.createElement('button');
    editButton.innerText = 'Update';
    editButton.addEventListener('click', () => updateEntry(user));
    cell.appendChild(editButton);

    return cell;
}

const createCell = (text) => {
    const cell = document.createElement('td');
    cell.innerText = text;
    return cell;
};

const renderEntries = () => {
    const display = document.querySelector('#entryDisplay');
    display.innerHTML = '';
    entries.forEach((user) => {
        const row = document.createElement('tr');
        row.appendChild(createCell(user.id));
        row.appendChild(createCell(new Date(user.name).toLocaleString()));
        row.appendChild(createActions(user));
        display.appendChild(row);
    });
};

document.addEventListener('DOMContentLoaded', function(){
    const createEntryForm = document.querySelector('#registerForm');
    createEntryForm.addEventListener('submit', createUser);
    indexUsers()
});