let userRole;
fetch('GiveRole')
    .then(res => res.json())
    .then(data => {
        userRole = data;
        // After retrieving userRole, fetch BookList
        fetch('BookList')
            .then(response => response.json())
            .then(data => {
                const tableBody = document.getElementById('bookTableBody');
                const tableHead = document.querySelector('#bookTable thead tr');
                let headContent = `
                    <th>Book ID</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Genre</th>
                    <th>Published</th>
                    <th>Price</th>
                    <th>Actions</th>
                `;
                if (userRole === 'admin') {
                    headContent = `
                        <th>Book ID</th>
                        <th>Title</th>
                        <th>Author</th>
                        <th>Genre</th>
                        <th>Published</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Actions</th>
                    `;
                }
                tableHead.innerHTML = headContent;

                data.forEach(book => {
                    const row = document.createElement('tr');

                    let content;
                    if (userRole === 'admin') {
                        content = `
                            <button type="button" class="edit_btn" onclick="editBook(${book.id})">Edit</button>
                            <button type="button" class="delete_btn" onclick="deleteBook(${book.id})">Delete</button>
                            `;
                        // Append table data for quantity if userRole is admin
                        row.innerHTML = `
                            <td>${book.id}</td>
                            <td>${book.title}</td>
                            <td>${book.author}</td>
                            <td>${book.genre}</td>
                            <td>${book.published_date}</td>
                            <td>${book.price}</td>
                            <td>${book.quantity}</td>
                            <td>${content}</td>
                            
                        `;
                    } else if (userRole === 'customer') {
                        content = `
                            <input type="number" id="select_quantity_${book.id}" placeholder="Select Quantity" value="">
                            <button type="button" class="addToCart" onclick="addToCart(${book.id},${book.price})">Add to Cart</button>
                            `;
                        // Append table data for quantity only if userRole is customer
                        row.innerHTML = `
                            <td>${book.id}</td>
                            <td>${book.title}</td>
                            <td>${book.author}</td>
                            <td>${book.genre}</td>
                            <td>${book.published_date}</td>
                            <td>${book.price}</td>
                            <td>${content}</td>
                        `;
                    }
                    
                    tableBody.appendChild(row);
                });
            })
            .catch(error => console.error('Error fetching BookList:', error));
    })
    .catch(error => console.error('Error fetching user role:', error));

const addToCart = (bookId,price) => {
    const selectedQuantity = document.getElementById(`select_quantity_${bookId}`).value;
       if (selectedQuantity <= 0) {
        alert('Please enter a valid quantity.');
        return;
    }
    fetch('AddToCart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `book_id=${bookId}&selected_quantity=${selectedQuantity}&price=${price}`
    })
    .then(response => {
        if (response.ok) {
            alert('Product added to cart successfully');
        } else {
            throw new Error('Failed to add product to cart');
        }
    })
    .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
        alert('An error occurred while adding the product to cart');
    });
}

const deleteBook = (bookId) => {
    fetch('DeleteBook', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `book_id=${bookId}`
    })
    .then(response => {
        if (response.ok) {
            alert('Book is deleted from the database');
        } else {
            throw new Error('Failed to delete the book');
        }
    })
    .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
        alert('An error occurred while deleting a book');
    });
}
const editBook = (bookId) => {
    // Get the form and its fields
    const formPopup = document.getElementById("formPopup");
    const titleInput = document.getElementById("title");
    const authorInput = document.getElementById("author");
    const genreInput = document.getElementById("genre");
    const publishedDateInput = document.getElementById("published_date");
    const priceInput = document.getElementById("price");
    const quantityInput = document.getElementById("quantity");
    const idInput = document.getElementById("id");

    // Show the form
    formPopup.style.display = "block";

    // Fetch book details based on bookId
    fetch('GetBookDetails', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `book_id=${bookId}`
    })
        .then(response => response.json())
        .then(book => {
            // Fill the form fields with book data
            titleInput.value = book.title;
            authorInput.value = book.author;
            genreInput.value = book.genre;
            publishedDateInput.value = book.published_date;
            priceInput.value = book.price;
            quantityInput.value = book.quantity;
            idInput.value = bookId;
        })
        .catch(error => {
            console.error('Error fetching book details:', error);
            // Handle error if necessary
        });

    // Close the form when close button is clicked
    const closeFormBtn = document.getElementById("closeFormBtn");
    closeFormBtn.addEventListener("click", function() {
        formPopup.style.display = "none";
    });

    // Close the form when clicking outside the form
    window.addEventListener("click", function(event) {
        if (event.target === formPopup) {
            formPopup.style.display = "none";
        }
    });
}
