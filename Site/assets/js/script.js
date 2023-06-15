function adicionarProduto(e){
    let a = e.target.parentNode.parentNode

    var src = a.querySelector("img").src
    var name = a.querySelectorAll("p")[0].innerText
    var price = a.querySelectorAll("p")[1].innerText

    produtosa = getCookie("produtos")

    if(produtosa == ""){
        produtosa = []
    }
    else{
        produtosa = JSON.parse(getCookie("produtos"))
    }
    produtosa.push({
        nome: name,
        img: src,
        prece: price, 
        quantidade: 1
    })
    setCookie("produtos", JSON.stringify(produtosa), 10)

    atualizacarrinho()    

}

function atualizacarrinho(){
    produtosa = getCookie("produtos")

    if(produtosa == ""){
        produtosa = []
    }
    else{
        produtosa = JSON.parse(getCookie("produtos"))
    }

    var doc = $("#listacarrinho"); 

    for(let i = 0; i < produtosa.length; i++){
        let str = `<li class="list-group-item">
        <div class="produto d-flex" style="max-height: 200px;">
                <img src="${produtosa[i].img}" class="" alt="Bootstrap" style="height: 30%; width: 35%;">
            <div class="desc p-3 pt-4">
                <p class="nomeprodut">${produtosa[i].nome}<a role="button" onclick="excloi(event)" class="btn-excud" href=""><i class="fa-solid fa-circle-xmark"></i></a></p>
                <div class="qtd" style="display: flex;">
                    <div class="additis d-flex align-items-center">
                        <a onclick="remoum(event)" class="btsadex"><i class="fa-solid fa-circle-minus fa-2x me-1"></i></a>                                
                        <p class="itenstot">${produtosa[i].quantidade}</p>
                        <a onclick="addum(event)" class="btsadex"><i class="fa-solid fa-circle-plus fa-2x ms-1"></i></a>
                    </div>
                    <p>${produtosa[i].prece}</p>
                </div>
            </div>
        </div>
    </li>`
    doc.append(str)

    }


}


function setCookie(cname, cvalue, exdays) {
const d = new Date();
d.setTime(d.getTime() + (exdays*24*60*60*1000));
let expires = "expires="+ d.toUTCString();
document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
let name = cname + "=";
let decodedCookie = decodeURIComponent(document.cookie);
let ca = decodedCookie.split(';');
for(let i = 0; i <ca.length; i++) {
    let c = ca[i];
    while (c.charAt(0) == ' ') {
    c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
    return c.substring(name.length, c.length);
    }
}
return "";
}  