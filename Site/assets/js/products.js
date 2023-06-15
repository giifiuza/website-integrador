
var data = 1;

document.getElementById("counting").innerText = data;

function increment() {
    data = data + 1;
    document.getElementById("counting").innerText = data;
}

function decrement() {
    data = data - 1;
    if (data < 1) {
        data = 1
    }
    document.getElementById("counting").innerText = data;
}

