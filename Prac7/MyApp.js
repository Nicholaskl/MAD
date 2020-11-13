$(window).on("load", function()
{
	console.log("Loaded");
	let opOne = document.getElementById("opOne");
	let opTwo = document.getElementById("opTwo");
	let result = document.getElementById("result");

	$("input[id=plus]").on("click", function() {
		let num1 = parseFloat(opOne.value);
		let num2 = parseFloat(opTwo.value);
		result.value = num1 + num2;

		if(isNaN(num1) || isNaN(num2)) {
			alert("One of the operands is not a number")
			result.value = "Error: Not a number!";
		}
	});

	$("input[id=minus]").on("click", function() {
		let num1 = parseFloat(opOne.value);
		let num2 = parseFloat(opTwo.value);
		result.value = num1 - num2;

		if(isNaN(num1) || isNaN(num2)) {
			alert("One of the operands is not a number")
			result.value = "Error: Not a number!";
		}
	});

	$("input[id=times]").on("click", function() {
		let num1 = parseFloat(opOne.value);
		let num2 = parseFloat(opTwo.value);
		result.value = num1 * num2;

		if(isNaN(num1) || isNaN(num2)) {
			alert("One of the operands is not a number")
			result.value = "Error: Not a number!";
		}
	});

	$("input[id=divide]").on("click", function() {
		let num1 = parseFloat(opOne.value);
		let num2 = parseFloat(opTwo.value);
		result.value = num1 / num2;

		if(isNaN(num1) || isNaN(num2)) {
			alert("One of the operands is not a number")
			result.value = "Error: Not a number!";
		}

		if(num2 === 0) {
			alert("Can't divide by 0!");
			result.value = "Error: Can't divide by 0!";
		}
	});
});