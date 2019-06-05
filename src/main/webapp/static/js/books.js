window.onload = function () {
    addNavBar();
    getBooks();

}
function getBooks() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = displayBooks;
    xhttp.open("GET", "books");
    xhttp.send();
    function displayBooks() {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            books = JSON.parse(xhttp.responseText);
            console.log(books);
            books.forEach(function (book) {
                addBookToTable(book);
            });
        }
    }
}
function addBookToTable(book) {
    var table = document.getElementById("books");
    var tr = document.createElement("tr");
    let td;
    //id
    addTableDef(book.id, tr);
    // cover
    td = document.createElement("td");
    tr.appendChild(td);
    let cover = document.createElement("img");
    if(book.cover!=null)
    	cover.src = book.cover;
    cover.alt = book.title;
    cover.width=90;
    td.appendChild(cover);
    // isbn10
    addTableDef(book.isbn10, tr);
    // isbn13
    addTableDef(book.isbn13, tr);
    // title
    addTableDef(book.title, tr);
    //authors
    addListToTable(tr, book.authors, (author) => `${author.first} ${author.last}`);
    //genres
    addListToTable(tr, book.genres, (genre) => `${genre.genre}`);
    //price
    addTableDef(book.price, tr);
    //stock
    addTableDef(book.stock, tr);
    // edit button
    td = document.createElement("td");
    let btn = document.createElement("button");
    tr.appendChild(td);
    td.appendChild(btn);
    btn.innerHTML="Edit"
    btn.className="btn btn-secondary emp-btn";
    btn.id="e_b_"+book.id;
    btn.addEventListener("click", editBook);
    btn.disabled=employee?false:true;
    // delete button
    td = document.createElement("td");
    btn = document.createElement("button");
    tr.appendChild(td);
    td.appendChild(btn);
    btn.innerHTML="X";
    btn.className="btn btn-danger emp-btn";
    btn.id="d_b_"+book.id;
    console.log("adding the button");
    btn.addEventListener("click",deleteBook);
    btn.disabled=employee?false:true;

    // actually append the row to the table.
    table.appendChild(tr);
}
function addTableDef(value, tr) {
    let td = document.createElement("td");
    td.innerHTML = value;
    tr.appendChild(td);
}

function addListToTable(tr, list, parser){
    td = document.createElement("td");
    let ul = document.createElement("ul");
    for (let i = 0; i< list.length; i++){
        let li = document.createElement("li");
        li.innerHTML=parser(list[i]);
        ul.appendChild(li);
    }
    td.appendChild(ul);
    tr.appendChild(td);
}

function editBook(){
    var btn = event.target;
    console.log(btn);
    console.log(btn.id);
    var id = btn.id.substring("e_b_".length);
    console.log(id);
    window.location.href="editBook/"+id;
}
function deleteBook(){
    var btn = event.target;
    var id = btn.id.substring("d_b_".length);
    var xhttp= new XMLHttpRequest();
    xhttp.onreadystatechange=deleteBooks;
    xhttp.open("DELETE", "books/"+id);
    let b = books.find(book=>book.id==id);
    console.log(b);
    xhttp.send(JSON.stringify(b));

    function deleteBooks() {
        if(xhttp.readyState===4&&xhttp.status===204)
            window.location.reload(true);
    }
}