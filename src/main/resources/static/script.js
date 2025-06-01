let items = [];

function loadItems() {
    fetch('/api/items')
        .then(response => response.json())
        .then(data => {
            items = data;
            renderItems();
        });
}

function renderItems() {
    const list = document.getElementById('itemList');
    list.innerHTML = '';
    items.forEach(item => {
        const li = document.createElement('li');
        li.className = item.purchased ? 'purchased' : '';
        li.innerHTML = `
            ${item.name}
            <button onclick="deleteItem(${item.id})">Delete</button>
            <button onclick="markPurchased(${item.id})">Mark Purchased</button>
        `;
        list.appendChild(li);
    });
}

function addItem() {
    const input = document.getElementById('itemInput');
    const name = input.value.trim();
    if (name) {
        fetch('/api/items', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ name, purchased: false })
        }).then(() => {
            input.value = '';
            loadItems();
        });
    }
}

function deleteItem(id) {
    fetch(`/api/items/${id}`, { method: 'DELETE' })
        .then(loadItems);
}

function markPurchased(id) {
    fetch(`/api/items/${id}/purchased`, { method: 'PUT' })
        .then(loadItems);
}

// Загружаем список при старте
document.addEventListener('DOMContentLoaded', loadItems);