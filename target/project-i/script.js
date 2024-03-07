let userRole;
fetch('GiveRole')
    .then(res => res.json())
    .then(data => {
        userRole = data;
        const navbar = document.getElementById('nav_bar');
        let content = '';
        if (userRole === 'admin') {
            content = `<a href="addbook.html">Add a New Book</a>`;
        }
        if (userRole === 'customer') {
            content = `<a href="viewcart.html">View Cart</a>`;
        }
         navbar.insertAdjacentHTML('beforeend', content);
    })
    .catch(error => console.error('Error fetching user role:', error));
