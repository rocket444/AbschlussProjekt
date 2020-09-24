let users = [];


const deleteUser = (id) => {
    fetch(`${URL}/users/${id}`, {
        method: 'DELETE'
    }).then(() => {
        indexUsers();
    });
};

const updateUser = (user) => {
    const formData = new FormData(user.target);
    user['username'] = formData.get('name');
    user['email'] = formData.get('email');
    user['password'] = formData.get('password');
    fetch(`${URL}/users/${user.id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    }).then((result) => {
        result.json().then((task) => {
            users = users.map((e) => e.id === task.id ? task : e);
            renderUsers();
        });
    });
}

const indexUsers = () => {
    fetch(`${URL}/users`, {
        method: 'GET'
    }).then((result) => {
        result.json().then((result) => {
            users = result;
            renderUsers();
        });
    });
    renderUsers();
};

const createActions = (task) => {
    const cell = document.createElement('td');

    const deleteButton = document.createElement('button');
    deleteButton.innerText = 'Delete';
    deleteButton.addEventListener('click', () => deleteUser(task.id));
    cell.appendChild(deleteButton);

    const editButton = document.createElement('button');
    editButton.innerText = 'Update';
    editButton.addEventListener('click', () => updateUser(task));
    cell.appendChild(editButton);

    return cell;
}

const createCell = (text) => {
    const cell = document.createElement('td');
    cell.innerText = text;
    return cell;
};

const renderUsers = () => {
    const display = document.querySelector('#adminUserDisplay');
    display.innerHTML = '';
    users.forEach((user) => {
        const row = document.createElement('tr');
        row.appendChild(createCell(user.id));
        row.appendChild(createCell(user.username));
        row.appendChild(createCell(user.email));
        row.appendChild(createActions(user));
        display.appendChild(row);
    });
};