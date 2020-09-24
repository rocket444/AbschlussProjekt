const URL = 'http://localhost:8080';
let tasks = [];

const createTask = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const task = {};
    task['description'] = formData.get('description');

    fetch(`${URL}/tasks`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(task)
    }).then((result) => {
        result.json().then((task) => {
            tasks.push(task);
        });
    });
    indexTasks()
};

const deleteTask = (id) => {
    fetch(`${URL}/tasks/${id}`, {
        method: 'DELETE'
    }).then(() => {
        indexTasks();
    });
};

const updateTask = (task) => {
    const formData = new FormData(task.target);
    task['description'] = formData.get('description');
    fetch(`${URL}/tasks/${task.id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(task)
    }).then((result) => {
        result.json().then((task) => {
            tasks = tasks.map((e) => e.id === task.id ? task : e);
            renderTasks();
        });
    });
}

const indexTasks = () => {
    fetch(`${URL}/tasks`, {
        method: 'GET'
    }).then((result) => {
        result.json().then((result) => {
            tasks = result;
            renderTasks();
        });
    });
    renderTasks();
};

const createActions = (task) => {
    const cell = document.createElement('td');

    const deleteButton = document.createElement('button');
    deleteButton.innerText = 'Delete';
    deleteButton.addEventListener('click', () => deleteTask(task.id));
    cell.appendChild(deleteButton);

    const editButton = document.createElement('button');
    editButton.innerText = 'Update';
    editButton.addEventListener('click', () => updateTask(task));
    cell.appendChild(editButton);

    return cell;
}

const createCell = (text) => {
    const cell = document.createElement('td');
    cell.innerText = text;
    return cell;
};

const renderTasks = () => {
    const display = document.querySelector('#taskDisplay');
    display.innerHTML = '';
    tasks.forEach((task) => {
        const row = document.createElement('tr');
        row.appendChild(createCell(task.id));
        row.appendChild(createCell(task.description));
        row.appendChild(createActions(task));
        display.appendChild(row);
    });
};

document.addEventListener('DOMContentLoaded', function(){
    const createEntryForm = document.querySelector('#taskForm');
    createEntryForm.addEventListener('submit', createTask);
    indexTasks()
});