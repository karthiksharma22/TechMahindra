let str_name = "digiterati";

for (let i = 0; i <= str_name.length; i++) {
  str_name =
    str_name[str_name.length - 1] + str_name.substring(0, str_name.length - 1);
  console.log(str_name);
}
