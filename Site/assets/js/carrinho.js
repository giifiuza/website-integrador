
var cartItems = [];


/*Adicionar item no carrinho */

function addToCart(title, price, image) {
    var existingItem = cartItems.find(function (item) {
        return item.title === title;
    });

    if (existingItem) {
        existingItem.quantity++;
    } else {
        var newItem = {
            title: title,
            price: price,
            quantity: 1,
            image: image
        };
    
        Swal.fire({
            title: 'Added to cart',
            imageUrl: '../assets/img/shopping-cart.png',
            imageWidth: 100,
            imageHeight: 100,
          })
        cartItems.push(newItem);
    }

    updateCart();
}

/* Remover item do carrinho */

function removeFromCart(title) {
    var itemIndex = cartItems.findIndex(function (item) {
        return item.title === title;
    });

    if (itemIndex !== -1) {
        cartItems.splice(itemIndex, 1);
    }

    updateCart();
}

/* Atualiza o carrinho para adicionar outros produtos ou remover */
function updateCart() {
    var cartItemsElement = document.getElementById('listacarrinho');
    var cartTotalElement = document.getElementById('cartTotal');
    cartItemsElement.innerHTML = '';

    var total = 0;

    cartItems.forEach(function (item) {
        var li = document.createElement('li');
        li.className = 'list-group-item';
        li.innerHTML = `
                    <div class="row">
                        <div class="col-2">
                            <img src="${item.image}" class="img-fluid" alt="Produto">
                        </div>
                        <div class="col-8">
                            <h6>${item.title}</h6>
                            <p>Price: $${item.price}</p>
                            <p>Amount: ${item.quantity}</p>
                        </div>
                        <div class="col-2">
                            <button class="btn btn-danger btn-sm" onclick="removeFromCart('${item.title}')">
                            <img src="../assets/img/remove.png" alt="Remover" width="20"></button>
                        </div>
                    </div>
                `;
        cartItemsElement.appendChild(li);

        total += item.price * item.quantity;
    });

    cartTotalElement.textContent = '$' + total.toFixed(2);
}

/* Finaliza compra, removendo todos os itens e mostrando um modal */

function checkout() {

    var total = 0;

    cartItems.forEach(function (item) {
        total += item.price * item.quantity;
    });


    document.getElementById('totalAmount').textContent = '$' + total.toFixed(2);


    var modal = new bootstrap.Modal(document.getElementById('checkoutModal'));
    modal.show();


    cartItems = [];
    updateCart();


    localStorage.removeItem('cartItems');
}


/* Consumindo API  */

function cep() {
    let cep = document.getElementById('cepInput').value;

    cep = cep.replace(/\D/g, '');

    if (cep.length === 8) {
        fetch(`https://viacep.com.br/ws/${cep}/json/`)
            .then(response => response.json())
            .then(data => {
                if (data.erro) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: 'We did not find your address. Try again',
                    });
                    document.getElementById('cepInput').value = '';
                } else {
                    Swal.fire({
                        icon: 'success',
                        title: 'Correct ZIP. We will send to that address',
                        html: `
                            <strong>Street:</strong> ${data.logradouro}<br>
                            <strong>Neighboard:</strong> ${data.bairro}<br>
                            <strong>City:</strong> ${data.localidade}<br>
                            <strong>State:</strong> ${data.uf}<br>
                        `,
                    });
                    document.getElementById('cepInput').value = '';
                }
            });
    } else {
        Swal.fire({
            icon: 'error',
            title: '404 Erro',
            text: 'Invalid Zip. Try again',
        });
        document.getElementById('cepInput').value = '';
    }
}
