function changeToDegFaren(celsius) {
  return (celsius * 9) / 5 + 32;
}

function changeToDegCel(fahrenheit) {
  return ((fahrenheit - 32) * 5) / 9;
}

let celsius = 60;
console.log(`${celsius}°C after changing is ${changeToDegFaren(celsius)}°F`);

let fahrenheitInput = 45;
console.log(
  `${fahrenheitInput}°F after changing is ${changeToDegCel(fahrenheitInput)}°C`
);
