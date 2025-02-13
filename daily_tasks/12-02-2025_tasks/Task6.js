function checkLeapYear(year) {
  if (year % 4 === 0) {
    if (year % 100 === 0) {
      return year % 400 === 0;
    }
    return true;
  }
  return false;
}

console.log(`Is 2016 a leap year? ${checkLeapYear(2016)}`);
console.log(`Is 2018 a leap year? ${checkLeapYear(2018)}`);
