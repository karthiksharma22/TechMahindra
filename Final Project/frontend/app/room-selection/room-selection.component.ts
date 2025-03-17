import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import * as THREE from 'three';
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule, RouterOutlet,Router,ActivatedRoute } from '@angular/router';


interface Room {
  id: number;
  roomNumber: string;
  floor: number;
  type: 'standard' | 'deluxe' | 'suite';
  hasSeaView: boolean;
  status: 'available' | 'booked' | 'selected';
  price: number;
  position: { x: number, y: number, z: number };
  dimensions: { width: number, height: number, depth: number };
}

interface UserData {
  roomId: number;
}

@Component({
  selector: 'app-room-selection',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    RouterOutlet
  ],
  templateUrl: './room-selection.component.html',
  styleUrls: ['./room-selection.component.css']
})
export class RoomSelectionComponent implements OnInit, AfterViewInit {
 
  @ViewChild('renderCanvas') renderCanvas!: ElementRef;
  
  scene!: THREE.Scene;
  camera!: THREE.PerspectiveCamera;
  renderer!: THREE.WebGLRenderer;
  controls!: OrbitControls;
  raycaster: THREE.Raycaster;
  pointer: THREE.Vector2;
  roomMeshes: THREE.Mesh[] = [];
  hotelId: number | null = null;
  rooms: Room[] = [];
  selectedRoom: Room | null = null;
  
  floors: number[] = [1, 2, 3, 4, 5];
  selectedFloor: number | null = null;
  showSeaViewOnly: boolean = false;
  roomTypes: string[] = ['standard', 'deluxe', 'suite'];
  selectedRoomType: string | null = null;
  
  isLoading: boolean = true;
  showFilters: boolean = false;
  
  constructor(private router: Router,private route:ActivatedRoute) {
    this.raycaster = new THREE.Raycaster();
    this.pointer = new THREE.Vector2();
    
  }

  ngOnInit(): void {
    this.initializeRooms();
    this.route.paramMap.subscribe(params => {
      this.hotelId = Number(params.get('id')); // Convert to number
      console.log('Hotel ID:', this.hotelId);
    });
  }

  ngAfterViewInit(): void {
    this.initThreeJS();
    this.createHotelModel();
    this.animate();
    
    setTimeout(() => {
      this.isLoading = false;
    }, 1500);
  }

  navigateToBooking() {
    const userData = localStorage.getItem('userData') || localStorage.getItem('employeeData');
    if (userData) {
      this.router.navigate(['/bookingform',this.hotelId]);
    } else {
      alert('You must be logged in to book a room.');
    }
  }

  initializeRooms(): void {
    let id = 1;
    for (let floor = 1; floor <= 5; floor++) {
      for (let room = 1; room <= 10; room++) {
        const leftType = this.getRoomType(room);
        const leftHasSeaView = room <= 5;
        
        this.rooms.push({
          id: id++,
          roomNumber: `${floor}${room.toString().padStart(2, '0')}L`,
          floor,
          type: leftType,
          hasSeaView: leftHasSeaView,
          status: Math.random() > 0.3 ? 'available' : 'booked',
          price: this.getRoomPrice(leftType, leftHasSeaView),
          position: { 
            x: -room * 2.5, 
            y: floor * 3, 
            z: leftHasSeaView ? -5 : 5 
          },
          dimensions: this.getRoomDimensions(leftType)
        });
        
        const rightType = this.getRoomType(room);
        const rightHasSeaView = room <= 5;
        
        this.rooms.push({
          id: id++,
          roomNumber: `${floor}${room.toString().padStart(2, '0')}R`,
          floor,
          type: rightType,
          hasSeaView: rightHasSeaView,
          status: Math.random() > 0.3 ? 'available' : 'booked',
          price: this.getRoomPrice(rightType, rightHasSeaView),
          position: { 
            x: room * 2.5, 
            y: floor * 3, 
            z: rightHasSeaView ? -5 : 5 
          },
          dimensions: this.getRoomDimensions(rightType)
        });
      }
    }
  }

  getRoomType(roomNumber: number): 'standard' | 'deluxe' | 'suite' {
    if (roomNumber <= 5) return 'standard';
    if (roomNumber <= 8) return 'deluxe';
    return 'suite';
  }

  getRoomPrice(type: string, hasSeaView: boolean): number {
    let basePrice = 0;
    switch(type) {
      case 'standard': basePrice = 100; break;
      case 'deluxe': basePrice = 200; break;
      case 'suite': basePrice = 350; break;
    }
    return hasSeaView ? basePrice * 1.5 : basePrice;
  }

  getRoomDimensions(type: string): { width: number, height: number, depth: number } {
    switch(type) {
      case 'standard': return { width: 2, height: 2.5, depth: 2 };
      case 'deluxe': return { width: 2.5, height: 2.5, depth: 2.5 };
      case 'suite': return { width: 3, height: 2.5, depth: 3 };
      default: return { width: 2, height: 2.5, depth: 2 };
    }
  }

  initThreeJS(): void {
    this.scene = new THREE.Scene();
    this.scene.background = new THREE.Color(0x87CEEB);
    
    const aspectRatio = this.renderCanvas.nativeElement.clientWidth / this.renderCanvas.nativeElement.clientHeight;
    this.camera = new THREE.PerspectiveCamera(75, aspectRatio, 0.1, 1000);
    this.camera.position.set(0, 15, 40);
    
    this.renderer = new THREE.WebGLRenderer({ 
      canvas: this.renderCanvas.nativeElement, 
      antialias: true 
    });
    this.renderer.setSize(
      this.renderCanvas.nativeElement.clientWidth, 
      this.renderCanvas.nativeElement.clientHeight
    );
    this.renderer.shadowMap.enabled = true;
    
    this.controls = new OrbitControls(this.camera, this.renderer.domElement);
    this.controls.enableDamping = true;
    this.controls.dampingFactor = 0.05;
    this.controls.minDistance = 10;
    this.controls.maxDistance = 100;
    this.controls.maxPolarAngle = Math.PI / 2;
    
    const ambientLight = new THREE.AmbientLight(0xffffff, 0.5);
    this.scene.add(ambientLight);
    
    const directionalLight = new THREE.DirectionalLight(0xffffff, 0.8);
    directionalLight.position.set(50, 50, 50);
    directionalLight.castShadow = true;
    this.scene.add(directionalLight);
    
    this.renderCanvas.nativeElement.addEventListener('click', (event: MouseEvent) => 
      this.onCanvasClick(event)
    );
    
    window.addEventListener('resize', () => this.onWindowResize());
  }

  createHotelModel(): void {
    const groundGeometry = new THREE.PlaneGeometry(100, 100);
    const groundMaterial = new THREE.MeshStandardMaterial({ 
      color: 0x358856,
      side: THREE.DoubleSide
    });
    const ground = new THREE.Mesh(groundGeometry, groundMaterial);
    ground.rotation.x = -Math.PI / 2;
    ground.position.y = -0.5;
    ground.receiveShadow = true;
    this.scene.add(ground);
    
    const oceanGeometry = new THREE.PlaneGeometry(100, 50);
    const oceanMaterial = new THREE.MeshStandardMaterial({ 
      color: 0x0077be,
      side: THREE.DoubleSide
    });
    const ocean = new THREE.Mesh(oceanGeometry, oceanMaterial);
    ocean.rotation.x = -Math.PI / 2;
    ocean.position.set(0, -0.4, -50);
    this.scene.add(ocean);
    
    const baseGeometry = new THREE.BoxGeometry(60, 2, 25);
    const baseMaterial = new THREE.MeshStandardMaterial({ color: 0xd3d3d3 });
    const base = new THREE.Mesh(baseGeometry, baseMaterial);
    base.position.y = 1;
    base.castShadow = true;
    base.receiveShadow = true;
    this.scene.add(base);

    const walkwayGeometry = new THREE.BoxGeometry(8, 20, 25);
    const walkwayMaterial = new THREE.MeshStandardMaterial({ color: 0xbebebe });
    const walkway = new THREE.Mesh(walkwayGeometry, walkwayMaterial);
    walkway.position.y = 10;
    this.scene.add(walkway);
    
    this.addDecorations();
    
    this.rooms.forEach(room => this.createRoomMesh(room));
  }

  addDecorations(): void {
    this.createPalmTree(-20, 0, -20);
    this.createPalmTree(-15, 0, -25);
    this.createPalmTree(-10, 0, -30);
    this.createPalmTree(10, 0, -30);
    this.createPalmTree(15, 0, -25);
    this.createPalmTree(20, 0, -20);
    
    const deskGeometry = new THREE.BoxGeometry(10, 1, 4);
    const deskMaterial = new THREE.MeshStandardMaterial({ color: 0x8B4513 });
    const desk = new THREE.Mesh(deskGeometry, deskMaterial);
    desk.position.set(0, 0.5, 15);
    desk.castShadow = true;
    desk.receiveShadow = true;
    this.scene.add(desk);
    
    const entranceGeometry = new THREE.BoxGeometry(8, 5, 1);
    const entranceMaterial = new THREE.MeshStandardMaterial({ color: 0x4B0082 });
    const entrance = new THREE.Mesh(entranceGeometry, entranceMaterial);
    entrance.position.set(0, 2.5, 20);
    this.scene.add(entrance);
    
    this.createBeachUmbrella(-10, 0, -35);
    this.createBeachUmbrella(0, 0, -35);
    this.createBeachUmbrella(10, 0, -35);
  }

  createPalmTree(x: number, y: number, z: number): void {
    const trunkGeometry = new THREE.CylinderGeometry(0.5, 0.7, 5, 8);
    const trunkMaterial = new THREE.MeshStandardMaterial({ color: 0x8B4513 });
    const trunk = new THREE.Mesh(trunkGeometry, trunkMaterial);
    trunk.position.set(x, y + 2.5, z);
    trunk.castShadow = true;
    this.scene.add(trunk);
    
    const leavesGeometry = new THREE.ConeGeometry(3, 4, 8);
    const leavesMaterial = new THREE.MeshStandardMaterial({ color: 0x228B22 });
    const leaves = new THREE.Mesh(leavesGeometry, leavesMaterial);
    leaves.position.set(x, y + 6, z);
    leaves.castShadow = true;
    this.scene.add(leaves);
  }

  createBeachUmbrella(x: number, y: number, z: number): void {
    const poleGeometry = new THREE.CylinderGeometry(0.2, 0.2, 3, 8);
    const poleMaterial = new THREE.MeshStandardMaterial({ color: 0xA9A9A9 });
    const pole = new THREE.Mesh(poleGeometry, poleMaterial);
    pole.position.set(x, y + 1.5, z);
    this.scene.add(pole);
    
    const umbrellaGeometry = new THREE.ConeGeometry(2, 1, 8, 1, true);
    const umbrellaMaterial = new THREE.MeshStandardMaterial({ 
      color: 0xFF6347, 
      side: THREE.DoubleSide 
    });
    const umbrella = new THREE.Mesh(umbrellaGeometry, umbrellaMaterial);
    umbrella.position.set(x, y + 3, z);
    umbrella.castShadow = true;
    this.scene.add(umbrella);
  }

  createRoomMesh(room: Room): void {
    const { width, height, depth } = room.dimensions;
    const geometry = new THREE.BoxGeometry(width, height, depth);
    
    let color;
    switch(room.status) {
      case 'available': color = 0xd3d3d3; break;
      case 'booked': color = 0x666666; break;
      case 'selected': color = 0x64b5f6; break;
    }
    
    const material = new THREE.MeshStandardMaterial({ color });
    const mesh = new THREE.Mesh(geometry, material);
    
    mesh.position.set(room.position.x, room.position.y, room.position.z);
    mesh.castShadow = true;
    mesh.receiveShadow = true;
    
    this.scene.add(mesh);
    this.roomMeshes.push(mesh);
    
    mesh.userData = { roomId: room.id } as UserData;
    
    if (room.hasSeaView) {
      const windowGeometry = new THREE.PlaneGeometry(width * 0.6, height * 0.4);
      const windowMaterial = new THREE.MeshStandardMaterial({ 
        color: 0x87CEFA, 
        transparent: true,
        opacity: 0.7
      });
      const windowMesh = new THREE.Mesh(windowGeometry, windowMaterial);
      windowMesh.position.set(
        mesh.position.x,
        mesh.position.y,
        mesh.position.z - depth/2 - 0.05
      );
      this.scene.add(windowMesh);
    }
    
    const doorGeometry = new THREE.PlaneGeometry(width * 0.4, height * 0.8);
    const doorMaterial = new THREE.MeshStandardMaterial({ color: 0x8B4513 });
    const doorMesh = new THREE.Mesh(doorGeometry, doorMaterial);
    
    const isLeftWing = room.position.x < 0;
    const doorOffset = isLeftWing ? depth/2 + 0.05 : -depth/2 - 0.05;
    const doorRotation = isLeftWing ? 0 : Math.PI;
    
    doorMesh.position.set(
      mesh.position.x,
      mesh.position.y - height * 0.1,
      mesh.position.z + doorOffset
    );
    doorMesh.rotation.y = doorRotation;
    this.scene.add(doorMesh);
    
    this.createRoomNumberDisplay(room);
  }

  createRoomNumberDisplay(room: Room): void {
    const boardGeometry = new THREE.BoxGeometry(1, 0.5, 0.1);
    const boardMaterial = new THREE.MeshStandardMaterial({ color: 0x8B4513 });
    const board = new THREE.Mesh(boardGeometry, boardMaterial);
    
    const isLeftWing = room.position.x < 0;
    const doorOffset = isLeftWing ? room.dimensions.depth/2 + 0.25 : -room.dimensions.depth/2 - 0.25;
    
    board.position.set(
      room.position.x + (isLeftWing ? -0.8 : 0.8),
      room.position.y,
      room.position.z + doorOffset
    );
    
    board.rotation.y = isLeftWing ? 0 : Math.PI;
    this.scene.add(board);
  }

  onCanvasClick(event: MouseEvent): void {
    const rect = this.renderCanvas.nativeElement.getBoundingClientRect();
    this.pointer.x = ((event.clientX - rect.left) / this.renderCanvas.nativeElement.clientWidth) * 2 - 1;
    this.pointer.y = -((event.clientY - rect.top) / this.renderCanvas.nativeElement.clientHeight) * 2 + 1;
    
    this.raycaster.setFromCamera(this.pointer, this.camera);
    
    const intersects = this.raycaster.intersectObjects(this.roomMeshes);
    
    if (intersects.length > 0) {
      const selectedMesh = intersects[0].object as THREE.Mesh;
      const roomId = (selectedMesh.userData as UserData).roomId;
      this.handleRoomSelection(roomId);
    }
  }

  handleRoomSelection(roomId: number): void {
    const room = this.rooms.find(r => r.id === roomId);
    
    if (!room || room.status === 'booked') return;
    
    if (this.selectedRoom) {
      this.selectedRoom.status = 'available';
      const selectedRoomId = this.selectedRoom.id;
      const index = this.roomMeshes.findIndex(mesh => 
        (mesh.userData as UserData).roomId === selectedRoomId
      );
      if (index !== -1) {
        (this.roomMeshes[index].material as THREE.MeshStandardMaterial).color.set(0xd3d3d3);
      }
    }
    
    if (room !== this.selectedRoom) {
      room.status = 'selected';
      this.selectedRoom = room;
      const index = this.roomMeshes.findIndex(mesh => 
        (mesh.userData as UserData).roomId === room.id
      );
      if (index !== -1) {
        (this.roomMeshes[index].material as THREE.MeshStandardMaterial).color.set(0x64b5f6);
      }
      this.focusOnRoom(room);
    } else {
      this.selectedRoom = null;
    }
  }

  filterRooms(): Room[] {
    return this.rooms.filter(room => {
      if (this.selectedFloor !== null && room.floor !== this.selectedFloor) return false;
      if (this.showSeaViewOnly && !room.hasSeaView) return false;
      if (this.selectedRoomType !== null && room.type !== this.selectedRoomType) return false;
      return true;
    });
  }

  clearFilters(): void {
    this.selectedFloor = null;
    this.showSeaViewOnly = false;
    this.selectedRoomType = null;
    this.updateRoomVisibility();
  }

  updateRoomVisibility(): void {
    const filteredRooms = this.filterRooms();
    const filteredIds = filteredRooms.map(room => room.id);
    
    this.roomMeshes.forEach(mesh => {
      const roomId = (mesh.userData as UserData).roomId;
      mesh.visible = filteredIds.includes(roomId);
   
    });
  }

  focusOnRoom(room: Room): void {
    const roomPosition = new THREE.Vector3(room.position.x, room.position.y, room.position.z);
    
    // Calculate offset to view the room from a good angle
    let offset;
    if (room.position.x < 0) { // Left wing
      offset = new THREE.Vector3(5, 2, 5); 
    } else { // Right wing
      offset = new THREE.Vector3(-5, 2, 5);
    }
    
    // Animate camera position
    const startPosition = this.camera.position.clone();
    const targetPosition = roomPosition.clone().add(offset);
    const duration = 1000; // milliseconds
    const startTime = Date.now();
    
    const animateCamera = () => {
      const elapsed = Date.now() - startTime;
      const progress = Math.min(elapsed / duration, 1);
      
      // Interpolate camera position
      this.camera.position.lerpVectors(startPosition, targetPosition, progress);
      
      // Look at the room
      this.controls.target.copy(roomPosition);
      this.controls.update();
      
      if (progress < 1) {
        requestAnimationFrame(animateCamera);
      }
    };
    
    animateCamera();
  }

  onWindowResize(): void {
    const aspectRatio = this.renderCanvas.nativeElement.clientWidth / this.renderCanvas.nativeElement.clientHeight;
    this.camera.aspect = aspectRatio;
    this.camera.updateProjectionMatrix();
    this.renderer.setSize(this.renderCanvas.nativeElement.clientWidth, this.renderCanvas.nativeElement.clientHeight);
  }

  animate(): void {
    requestAnimationFrame(() => this.animate());
    
    // Add subtle animation to water
    const oceanMaterials = this.scene.children
      .filter(child => child instanceof THREE.Mesh && 
              (child.material as THREE.MeshStandardMaterial).color.getHex() === 0x0077be)
              .map(mesh => ('material' in mesh ? (mesh as THREE.Mesh).material : null));

    
    if (oceanMaterials.length > 0) {
      const time = Date.now() * 0.001;
      const intensity = Math.sin(time) * 0.1 + 0.9;
      oceanMaterials.forEach(material => {
        (material as THREE.MeshStandardMaterial).emissiveIntensity = intensity;
      });
    }
    
    // Update controls
    this.controls.update();
    
    // Render scene
    this.renderer.render(this.scene, this.camera);
  }

  toggleFilters(): void {
    this.showFilters = !this.showFilters;
  }
  
  applyFilters(): void {
    this.updateRoomVisibility();
    this.showFilters = false;
  }
  
  resetView(): void {
    // Reset camera to default position and target
    const startPosition = this.camera.position.clone();
    const targetPosition = new THREE.Vector3(0, 15, 40);
    const duration = 1000; // milliseconds
    const startTime = Date.now();
    
    const startTarget = this.controls.target.clone();
    const endTarget = new THREE.Vector3(0, 0, 0);
    
    const animateReset = () => {
      const elapsed = Date.now() - startTime;
      const progress = Math.min(elapsed / duration, 1);
      
      // Interpolate camera position
      this.camera.position.lerpVectors(startPosition, targetPosition, progress);
      
      // Interpolate target
      const currentTarget = new THREE.Vector3();
      currentTarget.lerpVectors(startTarget, endTarget, progress);
      this.controls.target.copy(currentTarget);
      
      this.controls.update();
      
      if (progress < 1) {
        requestAnimationFrame(animateReset);
      }
    };
    
    animateReset();
  }
}