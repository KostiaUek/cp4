const getProducts = async() => {
    return fetch("/api/products").then(r => r.json())
}

const createHTMLEl = (product) => {
const template = `
    <h4>${product.name}</h4>
    <p>${product.description}</p>
    <span>${product.price}</span>
`
    const el = document.createElement('li')
    el.innerHTML = template.trim()
    return el;
}

(() => {
const productList = document.querySelector('.products')
    getProducts().then(products => products.map(createHTMLEl))
    .then(htmlProds => htmlProds.forEach(el => productList.appendChild(el)))
})();