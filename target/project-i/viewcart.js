fetch('ViewCart')
    .then(response => response.json())
    .then(data => {
        const tableBody = document.getElementById('bookTableBody');
        if(data.length===0){
            alert('Cart is empty. Nothing to show.');
            return;
        }
        data.forEach(book => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${book.id}</td>
                <td>${book.title}</td>
                <td>${book.author}</td>
                <td>${book.genre}</td>
                <td>${book.published_date}</td>
                <td>${book.price}</td>
                <td>${book.quantity}</td>

                <td><button type="button" class="remove_btn" onclick="removeBook(${book.id})">Remove</button></td>
            `;
            
            tableBody.appendChild(row);
        });
    })
    .catch(error => console.error('Error fetching BookList:', error));

const removeBook = (bookId)=>{
    fetch('RemoveBook', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: `book_id=${bookId}`
        })
        .then(response => {
            if (response.ok) {
                alert('Book is removed from Cart');
            } else {
                throw new Error('Failed to remove the book');
            }
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
            alert('An error occurred while removing a book from the cart');
        });
}
