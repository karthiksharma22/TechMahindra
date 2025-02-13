let today = new Date();
let date = today.getDate().toString().padStart(2, "0");
let month = (today.getMonth() + 1).toString().padStart(2, "0");
let year = today.getFullYear();

console.log(
  `month-date-year: ${month}-${date}-${year}, month/date/year: ${month}/${date}/${year}`
);
