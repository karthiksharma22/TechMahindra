startYear = 2014;
endYear = 2050;

for (let year = startYear; year <= endYear; year++) {
  const date = new Date(year, 0, 1);
  const dayOfWeek = date.getDay();

  if (dayOfWeek === 0) {
    console.log(`January 1st, ${year} is a Sunday.`);
  }
}
