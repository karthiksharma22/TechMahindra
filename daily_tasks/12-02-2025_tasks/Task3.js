let today = new Date();
let date = today.getDate();
let month = today.getMonth() + 1;
let year = today.getFullYear();

if (date < 10) {
  date = "0" + date;
}
if (month < 10) {
  month = "0" + month;
}

console.log(
  `month-date-year: ${month}-${date}-${year}, month/date/year: ${month}/${date}/${year}`
);

