/**
 * 
 */
function onClickShow(divName) {
	document.getElementById(divName).style.display = "block";
}
function onClickHide(divName) {
	document.getElementById(divName).style.display = "none";
}
function onCheckedShow(checkBox,divName) {
	document.getElementById(divName).style.display = checkBox.checked ? "block" : "none";
}
function onCheckedEnable(checkBox,fieldName) {
	document.getElementById(fieldName).disabled  = checkBox.checked ? false : true;
}
function onCheckedDisable(checkBox,fieldName) {
	document.getElementById(fieldName).disabled  = checkBox.checked ? true	: false;
}
function onClickEnable(fieldName) {
	document.getElementById(fieldName).disabled = false;
}
function onClickDisable(fieldName) {
	document.getElementById(fieldName).disabled = true;
}

function addRow(tableID) {
	var table = document.getElementById(tableID);
	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);

	var cell1 = row.insertCell(0);
	var element1 = document.createElement("input");
	element1.type = "text";
	element1.name = "fullName";
	/*element1.placeholder = "Full Name";*/
	element1.className = "form-control"
	cell1.appendChild(element1);

	var cell2 = row.insertCell(1);
	var element2 = document.createElement("input");
	element2.type = "number";
	element2.name = "ownership";
	/*element2.placeholder = "Owner Ship";*/
	element2.className = "form-control"
	cell2.appendChild(element2);

	var cell3 = row.insertCell(2);
	var element3 = document.createElement("input");
	element3.type = "text";
	element3.name = "nationality";
	/*element3.placeholder = "Nationality";*/
	element3.className = "form-control"
	cell3.appendChild(element3);

	var cell4 = row.insertCell(3);
	var element4 = document.createElement("button");
	element4.type = "button";
	element4.name = "clear";
	element4.className = "btn btn-danger btn-sm"
	element4.onclick = function() {
		deleteRow(this, 'shareHolderTable')
	}
	element4.innerHTML = '<span class="glyphicon glyphicon-remove"></span>'

	cell4.appendChild(element4);

}
function deleteRow(row, tableId) {
	var i = row.parentNode.parentNode.rowIndex;
	document.getElementById(tableId).deleteRow(i);
}

function showCheckBoxes(checkBox,fieldName) {
	var fields = document.getElementsByName(fieldName);
	if(checkBox.checked){
		for (var i = 0; i < fields.length; i++) {
	        fields[i].disabled = false;
	    }
	}else{
		for (var i = 0; i < fields.length; i++) {
	        fields[i].disabled = true;
	    }
	}
}

function deleteDiv(element) {
	alert("helo");
	element.parentElement.parentElement.parentElement.remove();
	onclick="deleteDiv(this);"
}