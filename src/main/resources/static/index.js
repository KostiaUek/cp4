const API_BASE = '/api';

// Fallback mocks
const MOCK_PRODUCTS = [
  {
    id: '11111111-1111-1111-1111-111111111111',
    name: 'Coffee Mug',
    description: 'A sturdy mug for your morning coffee.',
    price: 7.50,
    image: 'https://via.placeholder.com/200x140?text=Coffee+Mug'
  },
  {
    id: '22222222-2222-2222-2222-222222222222',
    name: 'Notebook',
    description: 'A ruled notebook for notes and sketches.',
    price: 3.25,
    image: 'https://via.placeholder.com/200x140?text=Notebook'
  },
  {
    id: '33333333-3333-3333-3333-333333333333',
    name: 'Water Bottle',
    description: 'Keeps your drinks cold for hours.',
    price: 12.00,
    image: 'https://via.placeholder.com/200x140?text=Water+Bottle'
  }
];

async function fetchJSON(path, options = {}) {
  const res = await fetch(API_BASE + path, {
    headers: { 'Content-Type': 'application/json' },
    ...options
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(`HTTP ${res.status}: ${text}`);
  }
  // handle empty bodies
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
    li.querySelector('button').onclick = async () => {
      await addProduct(p.id);
    };
    list.appendChild(li);
  });
}

async function loadProducts() {
  try {
    let products = await fetchJSON('/products');
    // if backend returns empty, fall back
    if (!products || products.length === 0) {
      throw new Error('no products');
    }
    renderProducts(products);
  } catch (e) {
    console.warn('Falling back to mock products:', e);
    renderProducts(MOCK_PRODUCTS);
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
    await loadOffer();  // refresh
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
