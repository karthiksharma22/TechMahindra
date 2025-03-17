export class Hotel {
    hid: number;
    hname: string;
    hloc: string;
    imageUrl:string;
    category:string;
    offers: string[]; 
    local_attractions: string[]; 
  
    constructor(hid: number,hname: string,hloc: string,imageUrl:string,category:string,offers: string[] = [],local_attractions: string[] = []) {
      this.hid = hid;
      this.hname = hname;
      this.hloc = hloc;
      this.imageUrl=imageUrl;
      this.category=category;
      this.offers = offers;
      this.local_attractions = local_attractions;
    }
  }