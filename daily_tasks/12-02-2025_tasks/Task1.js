const currentDateTime = () => {
  const days = [
    "Sunday",
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday",
  ];
  const curr_date = new Date();

  const day = days[curr_date.getDay()];

  let hours = curr_date.getHours();
  let minutes = curr_date.getMinutes();
  let seconds = curr_date.getSeconds();

  const amPm = hours >= 12 ? "PM" : "AM";
  hours = hours % 12 || 12;

  console.log(`Today is : ${day}`);
  console.log(
    `Current Time is : ${hours}${amPm} : ${minutes} min : ${seconds} sec`
  );
};

currentDateTime();
