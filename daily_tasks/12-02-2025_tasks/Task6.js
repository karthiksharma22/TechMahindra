function checkLeapYear(year) {
 if (n % 4 === 0) {
        if (n % 100 === 0) {
            return n % 400 === 0;
        }
        return true;
    }
    return false;
}

console.log(`Is 2016 a leap year? ${checkLeapYear(2016)}`);
console.log(`Is 2018 a leap year? ${checkLeapYear(2018)}`);
