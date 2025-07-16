import { useState, useEffect } from 'react';
import { Product, InventoryMovement, Category, Supplier, DashboardStats, ProductDTO, MovementDTO } from '../types/inventory';
import { productsApi, movementsApi, categoriesApi, suppliersApi, dashboardApi } from '../services/api';

export const useInventory = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [movements, setMovements] = useState<InventoryMovement[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [suppliers, setSuppliers] = useState<Supplier[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  // Load initial data
  useEffect(() => {
    loadAllData();
  }, []);

  const loadAllData = async () => {
    setLoading(true);
    setError(null);
    
    try {
      const [productsData, movementsData, categoriesData, suppliersData] = await Promise.all([
        productsApi.getAll(),
        movementsApi.getAll(),
        categoriesApi.getAll(),
        suppliersApi.getAll(),
      ]);

      setProducts(productsData);
      setMovements(movementsData);
      setCategories(categoriesData);
      setSuppliers(suppliersData);
    } catch (err) {
      setError('Error loading data from server');
      console.error('Error loading data:', err);
    } finally {
      setLoading(false);
    }
  };

  const addProduct = async (productData: ProductDTO): Promise<Product> => {
    try {
      const newProduct = await productsApi.create(productData);
      setProducts(prev => [...prev, newProduct]);
      return newProduct;
    } catch (err) {
      setError('Error creating product');
      throw err;
    }
  };

  const updateProduct = async (id: string, updates: Partial<ProductDTO>): Promise<Product> => {
    try {
      const updatedProduct = await productsApi.update(id, updates);
      setProducts(prev => 
        prev.map(product => 
          product.id === id ? updatedProduct : product
        )
      );
      return updatedProduct;
    } catch (err) {
      setError('Error updating product');
      throw err;
    }
  };

  const deleteProduct = async (id: string): Promise<void> => {
    try {
      await productsApi.delete(id);
      setProducts(prev => prev.filter(product => product.id !== id));
    } catch (err) {
      setError('Error deleting product');
      throw err;
    }
  };

  const addMovement = async (movementData: MovementDTO): Promise<InventoryMovement> => {
    try {
      const newMovement = await movementsApi.create(movementData);
      setMovements(prev => [...prev, newMovement]);
      
      // Refresh products to get updated stock
      const updatedProducts = await productsApi.getAll();
      setProducts(updatedProducts);
      
      return newMovement;
    } catch (err) {
      setError('Error creating movement');
      throw err;
    }
  };

  const getDashboardStats = async (): Promise<DashboardStats> => {
    try {
      const stats = await dashboardApi.getStats();
      return stats;
    } catch (err) {
      setError('Error loading dashboard stats');
      throw err;
    }
  };

  // Helper function to convert movement types from API to frontend format
  const convertMovementType = (apiType: string): 'entry' | 'positive_adjustment' | 'negative_adjustment' | 'return' => {
    switch (apiType) {
      case 'ENTRY':
        return 'entry';
      case 'POSITIVE_ADJUSTMENT':
        return 'positive_adjustment';
      case 'NEGATIVE_ADJUSTMENT':
        return 'negative_adjustment';
      case 'RETURN':
        return 'return';
      default:
        return 'entry';
    }
  };

  // Helper function to convert movement types from frontend to API format
  const convertMovementTypeToApi = (frontendType: string): 'ENTRY' | 'POSITIVE_ADJUSTMENT' | 'NEGATIVE_ADJUSTMENT' | 'RETURN' => {
    switch (frontendType) {
      case 'entry':
        return 'ENTRY';
      case 'positive_adjustment':
        return 'POSITIVE_ADJUSTMENT';
      case 'negative_adjustment':
        return 'NEGATIVE_ADJUSTMENT';
      case 'return':
        return 'RETURN';
      default:
        return 'ENTRY';
    }
  };

  // Transform movements for frontend compatibility
  const transformedMovements = movements.map(movement => ({
    ...movement,
    type: convertMovementType(movement.type),
    date: new Date(movement.date),
  }));

  // Transform products for frontend compatibility
  const transformedProducts = products.map(product => ({
    ...product,
    createdAt: new Date(product.createdAt),
    updatedAt: new Date(product.updatedAt),
  }));

  return {
    products: transformedProducts,
    movements: transformedMovements,
    categories,
    suppliers,
    loading,
    error,
    addProduct,
    updateProduct,
    deleteProduct,
    addMovement: async (movementData: any) => {
      const apiMovementData: MovementDTO = {
        ...movementData,
        type: convertMovementTypeToApi(movementData.type),
      };
      return addMovement(apiMovementData);
    },
    getDashboardStats,
    refreshData: loadAllData,
  };
};