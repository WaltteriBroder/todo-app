function showTasks(completed) {
    const taskList = document.getElementById('taskList');
    const completedTaskList = document.getElementById('completedTaskList');

    if (completed) {
        taskList.style.display = 'none';
        completedTaskList.style.display = 'block';
    } else {
        taskList.style.display = 'block';
        completedTaskList.style.display = 'none';
    }
}

function openModal(modalId) {
    const modal = document.getElementById(modalId);
    modal.style.display = "flex";
    modal.classList.add("show");
}

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    modal.classList.remove("show");
    setTimeout(() => (modal.style.display = "none"), 400);
}

function toggleTaskDetails(button) {
    const details = button.parentElement.nextElementSibling;
    if (details.style.display === "block") {
        details.style.display = "none";
    } else {
        details.style.display = "block";
    }
}

function editTaskTitle(taskId, newTitle) {
    fetch(`/edit/title/${taskId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({ title: newTitle })
    }).then(response => {
        if (!response.ok) {
            alert("Failed to update title");
        }
    });
}

function editTaskDescription(taskId, newDescription) {
    fetch(`/edit/description/${taskId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({ description: newDescription })
    }).then(response => {
        if (!response.ok) {
            alert("Failed to update description");
        }
    });
}


