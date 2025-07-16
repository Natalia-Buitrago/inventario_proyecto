const API_BASE_URL = 'http://localhost:8080/api';

// API Response types
export interface ApiResponse<T> {
  data: T;
  status: number;
}

// API Error type
export interface ApiError {
  message: string;
  status: number;
}

// Generic API request function
async function apiRequest<T>(
  endpoint: string,
  options: RequestInit = {}
): Promise<T> {
  const url = `${API_BASE_URL}${endpoint}`;
  
  const defaultOptions: RequestInit = {
    headers: {
      'Content-Type': 'application/json',
      ...options.headers,
    },
    ...options,
  };

  try {
    const response = await fetch(url, defaultOptions);
    
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    
    // Manejar respuesta vacía (por ejemplo, DELETE)
    const text = await response.text();
    return text ? JSON.parse(text) : ({} as T);
  } catch (error) {
    console.error('API request failed:', error);
    throw error;
  }
}

// Products API
export const productsApi = {
  getAll: () => apiRequest<any[]>('/products'),
  
  getById: (id: string) => apiRequest<any>(`/products/${id}`),
  
  getByCode: (code: string) => apiRequest<any>(`/products/code/${code}`),
  
  getByCategory: (category: string) => apiRequest<any[]>(`/products/category/${category}`),
  
  getBySupplier: (supplier: string) => apiRequest<any[]>(`/products/supplier/${supplier}`),
  
  getLowStock: () => apiRequest<any[]>('/products/low-stock'),
  
  search: (term: string) => apiRequest<any[]>(`/products/search?term=${encodeURIComponent(term)}`),
  
  create: (product: any) => apiRequest<any>('/products', {
    method: 'POST',
    body: JSON.stringify(product),
  }),
  
  update: (id: string, product: any) => apiRequest<any>(`/products/${id}`, {
    method: 'PUT',
    body: JSON.stringify(product),
  }),
  
  delete: (id: string) => apiRequest<void>(`/products/${id}`, {
    method: 'DELETE',
  }),
  
  updateStock: (id: string, stock: number) => apiRequest<any>(`/products/${id}/stock?stock=${stock}`, {
    method: 'PATCH',
  }),
};

// Movements API
export const movementsApi = {
  getAll: () => apiRequest<any[]>('/movements'),
  
  getById: (id: string) => apiRequest<any>(`/movements/${id}`),
  
  getByProductId: (productId: string) => apiRequest<any[]>(`/movements/product/${productId}`),
  
  getByType: (type: string) => apiRequest<any[]>(`/movements/type/${type}`),
  
  getRecent: (days: number = 7) => apiRequest<any[]>(`/movements/recent?days=${days}`),
  
  getLatest: () => apiRequest<any[]>('/movements/latest'),
  
  create: (movement: any) => apiRequest<any>('/movements', {
    method: 'POST',
    body: JSON.stringify(movement),
  }),
};

// Categories API
export const categoriesApi = {
  getAll: () => apiRequest<any[]>('/categories'),
  
  getById: (id: string) => apiRequest<any>(`/categories/${id}`),
  
  getByName: (name: string) => apiRequest<any>(`/categories/name/${encodeURIComponent(name)}`),
  
  create: (category: any) => apiRequest<any>('/categories', {
    method: 'POST',
    body: JSON.stringify(category),
  }),
  
  update: (id: string, category: any) => apiRequest<any>(`/categories/${id}`, {
    method: 'PUT',
    body: JSON.stringify(category),
  }),
  
  delete: (id: string) => apiRequest<void>(`/categories/${id}`, {
    method: 'DELETE',
  }),
};

// Suppliers API
export const suppliersApi = {
  getAll: () => apiRequest<any[]>('/suppliers'),
  
  getById: (id: string) => apiRequest<any>(`/suppliers/${id}`),
  
  getByName: (name: string) => apiRequest<any>(`/suppliers/name/${encodeURIComponent(name)}`),
  
  create: (supplier: any) => apiRequest<any>('/suppliers', {
    method: 'POST',
    body: JSON.stringify(supplier),
  }),
  
  update: (id: string, supplier: any) => apiRequest<any>(`/suppliers/${id}`, {
    method: 'PUT',
    body: JSON.stringify(supplier),
  }),
  
  delete: (id: string) => apiRequest<void>(`/suppliers/${id}`, {
    method: 'DELETE',
  }),
};

// Dashboard API
export const dashboardApi = {
  getStats: () => apiRequest<any>('/dashboard/stats'),
};