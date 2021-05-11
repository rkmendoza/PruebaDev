const uri = "http://localhost:8080/api/product";
let products = [];

function getProductsItems() {
  fetch(uri+"/getAllProducts")
    .then(response => response.json())
    .then(data => _displayItems(data.products))
    .catch(error => console.error("Error to get products.", error));
}

function _displayItems(data) {
  const tBody = document.getElementById("products");
  tBody.innerHTML = "";
  _displayCount(data.length);
  const button = document.createElement("button");
  console.log(data);

  data.forEach(item => {
    let editButton = document.createElement("a");
    editButton.href = "#editProductModal";
    editButton.className = "edit";
    editButton.setAttribute("onclick", `displayEditForm(${item.id})`);
    editButton.setAttribute("data-toggle", "modal");
    editButton.innerHTML =
      "<i class='material-icons' data-toggle='tooltip' title='Edit'>&#xE254;</i>";

    let deleteButton = document.createElement("a");
    deleteButton.href = "#deleteProductModal";
    deleteButton.className = "delete";
    deleteButton.setAttribute("onclick", `displayDeleteForm(${item.id})`);
    deleteButton.setAttribute("data-toggle", "modal");
    deleteButton.innerHTML =
      "<i class='material-icons' data-toggle='tooltip' title='Delete'>&#xE872;</i>";

    let tr = tBody.insertRow();

    let td1 = tr.insertCell(0);
    let textId = document.createTextNode(item.id);

    td1.appendChild(textId);

    let td2 = tr.insertCell(1);
    let textNombre = document.createTextNode(item.nombre);
    td2.appendChild(textNombre);

    let td3 = tr.insertCell(2);
    let textTipo = document.createTextNode(item.tipo);
    td3.appendChild(textTipo);

    let td4 = tr.insertCell(3);
    let textCantidad = document.createTextNode(item.cantidad);
    td4.appendChild(textCantidad);

    let td5 = tr.insertCell(4);
    let textCosto = document.createTextNode(item.costo);
    td5.appendChild(textCosto);

	let td6 = tr.insertCell(5);
    let textPrice = document.createTextNode(item.precio);
    td6.appendChild(textPrice);

	let td7 = tr.insertCell(6);
    let textDate = document.createTextNode(item.fecha);
    td7.appendChild(textDate);

    let td8 = tr.insertCell(7);
    td8.appendChild(editButton);
    td8.appendChild(deleteButton);
  });

  products = data;
}

function addProductItem() {
	
  const nombreInputText = document.getElementById("add-nombre");
  const tipoInputText = document.getElementById("add-tipo");
  const cantidadInputText = document.getElementById("add-cantidad");
  const costoInputText = document.getElementById("add-costo");
  const fechaInputText = document.getElementById("add-fecha");

  const item = {
    nombre: nombreInputText.value.trim(),
    tipo: tipoInputText.value.trim(),
    cantidad: cantidadInputText.value.trim(),
    costo: parseFloat(costoInputText.value.trim()),
	fecha: fechaInputText.value.trim()
  };
  console.log(JSON.stringify(item));
  fetch(uri+"/createProduct", {
    method: "POST",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json"
    },
    body: JSON.stringify(item)
  })
    .then(response => response.json())
    .then(() => {
      getProductsItems();
      nombreInputText.value = "";
      tipoInputText.value = "";
      cantidadInputText.value = "";
      costoInputText.value = "";
      fechaInputText.value = "";
    })
    .catch(error => console.error("Error to add Product.", error));
}

function deleteProductItem() {
  const itemId = document.getElementById("delete-id").value.trim();
  console.log("id deleteProductItem " + itemId);
  fetch(uri+"/delete/"+itemId, {
    method: "DELETE"
  })
    .then(() => getProductsItems())
    .catch(error => console.error("Error to delete Product.", error));
}

function displayDeleteForm(id) {
  const item = products.find(item => item.id === id);
  console.log("id displayDeleteForm " + item.id);
  document.getElementById("delete-id").value = item.id;
}

function displayEditForm(id) {
  const item = products.find(item => item.id === id);
  console.log("id " + item.id);
  document.getElementById("edit-id").value = item.id;
  document.getElementById("edit-nombre").value = item.nombre;
  document.getElementById("edit-tipo").value = item.tipo;
  document.getElementById("edit-cantidad").value = item.cantidad;
  document.getElementById("edit-costo").value = item.costo;
  document.getElementById("edit-precio").value = item.precio;
  document.getElementById("edit-fecha").value = item.fecha;
}

function updateProductItem() {
  const itemId = document.getElementById("edit-id").value.trim();
  const item = {
    id: parseInt(itemId, 10),
    id: document.getElementById("edit-id").value.trim(),
    nombre: document.getElementById("edit-nombre").value.trim(),
    tipo: document.getElementById("edit-tipo").value.trim(),
    cantidad: document.getElementById("edit-cantidad").value.trim(),
    costo: document.getElementById("edit-costo").value.trim(),
	precio: document.getElementById("edit-precio").value.trim(),
	fecha: document.getElementById("edit-fecha").value.trim()
  };

  fetch(uri+"/update/"+itemId, {
    method: "PUT",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json"
    },
    body: JSON.stringify(item)
  })
    .then(() => getProductsItems())
    .catch(error => console.error("Error to update item.", error));

  return false;
}

function _displayCount(itemCount) {
  const name = itemCount === 1 ? "entry" : "entries";
  document.getElementById(
    "counter"
  ).innerHTML = `Showing <b>${itemCount}</b> ${name}`;
}

