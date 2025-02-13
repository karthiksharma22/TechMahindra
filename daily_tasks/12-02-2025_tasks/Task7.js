const current_date = new Date();
let curr_year = current_date.getFullYear();
let christmas_date = new Date(curr_year, 11, 25);

if (current_date > christmas_date) {
  curr_year++;
  christmas_date = new Date(curr_year, 11, 25);
}

const daysLeft = Math.ceil(
  (christmas_date - current_date) / (1000 * 3600 * 24)
);

console.log(
  `${daysLeft} days are left before the Christmas which lies on ${christmas_date}`
);
