//calculating which 1st of january between the years 2014 - 2025 falls on a sunday

for (let year = 2014; year <= 2025; year++) {
  const date = new Date(year, 0, 1);
  const dayOfWeek = date.getDay();

  if (dayOfWeek === 0) {
    console.log(`1st January of the year ${year} is a Sunday.`);
  }
}
