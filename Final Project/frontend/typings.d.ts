declare module 'panolens' {
    import * as THREE from 'three';
  
    export class Panorama extends THREE.Object3D {
      constructor(image: string);
    }
  
    export class ImagePanorama extends Panorama {
      constructor(image: string);
    }
  
    export class Viewer {
      constructor(options?: { 
        container?: HTMLElement;
        autoRotate?: boolean;
        autoRotateSpeed?: number;
        controlBar?: boolean;
      });
  
      add(panorama: Panorama): void;
      setPanorama(panorama: Panorama): void;
    }
  }