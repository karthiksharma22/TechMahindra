function checkLeapYear(year) {
  return year % 100 === 0 ? year % 400 === 0 : year % 4 === 0;
}

console.log(`Is 2016 a leap year? ${checkLeapYear(2016)}`);
console.log(`Is 2018 a leap year? ${checkLeapYear(2018)}`);
