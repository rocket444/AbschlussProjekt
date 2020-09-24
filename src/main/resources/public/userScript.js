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

document.addEventListener('DOMContentLoaded', function () {
    const createEntryForm = document.querySelector('#registerForm');
    createEntryForm.addEventListener('submit', createUser);
    indexUsers()
});