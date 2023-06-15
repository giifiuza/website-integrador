
var cartItems = [];

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
                cartItems.push(newItem);
            }

            updateCart();
        }

        function removeFromCart(title) {
            var itemIndex = cartItems.findIndex(function (item) {
                return item.title === title;
            });

            if (itemIndex !== -1) {
                cartItems.splice(itemIndex, 1);
            }

            updateCart();
        }

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
                            <p>Preço: $${item.price}</p>
                            <p>Quantidade: ${item.quantity}</p>
                        </div>
                        <div class="col-2">
                            <button class="btn btn-danger btn-sm" onclick="removeFromCart('${item.title}')">
                            <img src="./images/remove.png" alt="Remover" width="20"></button>
                        </div>
                    </div>
                `;
                cartItemsElement.appendChild(li);

                total += item.price * item.quantity;
            });

            cartTotalElement.textContent = '$' + total.toFixed(2);
        }

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