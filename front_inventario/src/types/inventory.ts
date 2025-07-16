export interface Product {
  id: string;
  code: string;
  name: string;
  description: string;
  category: string;
  supplier: string;
  purchasePrice: number;
  salePrice: number;
  unit: string;
  currentStock: number;
  minimumStock: number;
  createdAt: string;
  updatedAt: string;
}

export interface Category {
  id: string;
  name: string;
  description: string;
}

export interface Supplier {
  id: string;
  name: string;
  contact: string;
  phone: string;
  email: string;
}

export interface InventoryMovement {
  id: string;
  productId: string;
  productName: string;
  productCode: string;
  type: 'ENTRY' | 'POSITIVE_ADJUSTMENT' | 'NEGATIVE_ADJUSTMENT' | 'RETURN';
  quantity: number;
  previousStock: number;
  newStock: number;
  date: string;
  comments?: string;
  createdBy: string;
}

export interface DashboardStats {
  totalProducts: number;
  totalValue: number;
  lowStockItems: number;
  recentMovements: number;
  categories: { name: string; count: number }[];
}

// DTOs for API requests
export interface ProductDTO {
  code: string;
  name: string;
  description: string;
  category: string;
  supplier: string;
  purchasePrice: number;
  salePrice: number;
  unit: string;
  minimumStock: number;
}

export interface MovementDTO {
  productId: string;
  type: 'ENTRY' | 'POSITIVE_ADJUSTMENT' | 'NEGATIVE_ADJUSTMENT' | 'RETURN';
  quantity: number;
  comments?: string;
  createdBy: string;
}