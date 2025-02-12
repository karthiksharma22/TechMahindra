for (let year = 2014; year <= 2050; year++) {
  const date = new Date(year, 0, 1);
  const dayOfWeek = date.getDay();

  if (dayOfWeek === 0) {
    console.log(`January 1st, ${year} is a Sunday.`);
  }
}
