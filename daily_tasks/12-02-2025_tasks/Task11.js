// converting deg Celcius to deg Farenheit and vice versa

function changeToDegFaren(celsius) {
  return (celsius * 9) / 5 + 32;
}

function changeToDegCel(fahrenheit) {
  return ((fahrenheit - 32) * 5) / 9;
}

let celsius = 65;
console.log(`${celsius}°C after changing is ${changeToDegFaren(celsius)}°F`);

let fahrenheitInput = 35;
console.log(
  `${fahrenheitInput}°F after changing is ${changeToDegCel(fahrenheitInput)}°C`
);
