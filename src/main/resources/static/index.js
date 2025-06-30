const API_BASE = '/api';

async function fetchJSON(path, options = {}) {
  const res = await fetch(API_BASE + path, {
    headers: { 'Content-Type': 'application/json' },
    ...options
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(`HTTP ${res.status}: ${text}`);
  }
  // handle empty bodies (e.g. 204 No Content)
  if (res.status === 204 || res.headers.get('Content-Length') === '0') {
    return null;
  }
  return res.json();
}

async function loadVersion() {
  const el = document.getElementById('version');
  el.textContent = '…';
  try {
    const res = await fetch(API_BASE + '/version');
    if (!res.ok) throw new Error(res.status);
    el.textContent = await res.text();
  } catch (e) {
    el.textContent = 'Error';
    console.error('loadVersion:', e);
  }
}

function renderProducts(products) {
  const list = document.getElementById('products');
  list.innerHTML = '';
  products.forEach(p => {
    const li = document.createElement('li');
    li.className = 'product-card';
    li.innerHTML = `
      <img src="${p.image || 'https://via.placeholder.com/200x140'}" alt="${p.name}" />
      <h4>${p.name}</h4>
      <p>${p.description}</p>
      <div class="price">$${(+p.price).toFixed(2)}</div>
      <button>Add to Cart</button>
    `;
    li.querySelector('button').onclick = () => addProduct(p.id);
    list.appendChild(li);
  });
}

async function loadProducts() {
  try {
    const products = await fetchJSON('/products');
    if (!products || !products.length) {
      throw new Error('No products returned');
    }
    renderProducts(products);
  } catch (e) {
    console.error('loadProducts:', e);
    const list = document.getElementById('products');
    list.innerHTML = '<li>Error loading products</li>';
  }
}

async function loadOffer() {
  const pre = document.getElementById('offer');
  pre.textContent = 'Loading…';
  try {
    const offer = await fetchJSON('/current-offer');
    pre.textContent = JSON.stringify(offer, null, 2);
  } catch (e) {
    pre.textContent = 'Error loading offer';
    console.error('loadOffer:', e);
  }
}

async function addProduct(productId) {
  try {
    await fetchJSON(`/add-product/${encodeURIComponent(productId)}`, { method: 'POST' });
    await loadOffer();
  } catch (e) {
    alert('Failed to add product: ' + e.message);
    console.error('addProduct:', e);
  }
}

async function submitAcceptOffer(cmd) {
  const resEl = document.getElementById('reservation');
  resEl.textContent = 'Sending…';
  try {
    const details = await fetchJSON('/accept-offer', {
      method: 'POST',
      body: JSON.stringify(cmd)
    });
    resEl.textContent = JSON.stringify(details, null, 2);
  } catch (e) {
    resEl.textContent = 'Error: ' + e.message;
    console.error('submitAcceptOffer:', e);
  }
}

document.addEventListener('DOMContentLoaded', () => {
  loadVersion();
  loadProducts();
  loadOffer();

  document.getElementById('accept-offer-form')
    .addEventListener('submit', e => {
      e.preventDefault();
      const cmd = {
        firstName: document.getElementById('firstName').value,
        lastName:  document.getElementById('lastName').value,
        email:     document.getElementById('email').value
      };
      submitAcceptOffer(cmd);
    });
});
