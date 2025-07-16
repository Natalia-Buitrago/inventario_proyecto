import React, { useState, useEffect } from 'react';
import { Package, TrendingUp, AlertTriangle, Activity, BarChart3 } from 'lucide-react';
import { useInventory } from '../hooks/useInventory';
import { DashboardStats } from '../types/inventory';

const Dashboard: React.FC = () => {
  const { products, movements, loading, error } = useInventory();
  const [stats, setStats] = useState<DashboardStats | null>(null);

  useEffect(() => {
    loadDashboardStats();
  }, []);

  const loadDashboardStats = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/dashboard/stats');
      const dashboardStats = await response.json();
      setStats(dashboardStats);
    } catch (err) {
      console.error('Error loading dashboard stats:', err);
    }
  };

  const lowStockProducts = products.filter(product => product.currentStock <= product.minimumStock);
  const recentMovements = movements.slice(-5).reverse();
  
  // Calculate total value from products
  const totalValue = products.reduce((sum, product) => sum + (product.currentStock * product.salePrice), 0);

  const StatCard: React.FC<{
    title: string;
    value: string | number;
    icon: React.ReactNode;
    color: string;
    change?: string;
  }> = ({ title, value, icon, color, change }) => (
    <div className="bg-white rounded-lg shadow-md p-6 border border-gray-200">
      <div className="flex items-center justify-between">
        <div>
          <p className="text-sm font-medium text-gray-600">{title}</p>
          <p className="text-2xl font-bold text-gray-900">{value}</p>
          {change && (
            <p className="text-sm text-green-600 mt-1">
              {change}
            </p>
          )}
        </div>
        <div className={`p-3 rounded-full ${color}`}>
          {icon}
        </div>
      </div>
    </div>
  );

  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="animate-spin rounded-full h-32 w-32 border-b-2 border-blue-600"></div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="bg-red-50 border border-red-200 rounded-lg p-4">
        <div className="flex items-center">
          <AlertTriangle className="h-5 w-5 text-red-600 mr-2" />
          <span className="text-red-800">{error}</span>
        </div>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      {/* Stats Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <StatCard
          title="Total Productos"
          value={stats?.totalProducts || products.length}
          icon={<Package className="h-6 w-6 text-blue-600" />}
          color="bg-blue-100"
        />
        <StatCard
          title="Valor Total"
          value={`$${(stats?.totalValue || totalValue).toLocaleString()}`}
          icon={<TrendingUp className="h-6 w-6 text-green-600" />}
          color="bg-green-100"
        />
        <StatCard
          title="Stock Bajo"
          value={stats?.lowStockItems || lowStockProducts.length}
          icon={<AlertTriangle className="h-6 w-6 text-red-600" />}
          color="bg-red-100"
        />
        <StatCard
          title="Movimientos (7 días)"
          value={stats?.recentMovements || 0}
          icon={<Activity className="h-6 w-6 text-purple-600" />}
          color="bg-purple-100"
        />
      </div>

      {/* Content Grid */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Categories Chart */}
        <div className="bg-white rounded-lg shadow-md p-6 border border-gray-200">
          <div className="flex items-center justify-between mb-4">
            <h3 className="text-lg font-semibold text-gray-900">Productos por Categoría</h3>
            <BarChart3 className="h-5 w-5 text-gray-500" />
          </div>
          <div className="space-y-3">
            {stats?.categories?.map((category, index) => (
              <div key={category.name} className="flex items-center justify-between">
                <span className="text-sm text-gray-600">{category.name}</span>
                <div className="flex items-center space-x-2">
                  <div className="w-20 bg-gray-200 rounded-full h-2">
                    <div
                      className="bg-blue-600 h-2 rounded-full"
                      style={{ width: `${stats.totalProducts > 0 ? (category.count / stats.totalProducts) * 100 : 0}%` }}
                    />
                  </div>
                  <span className="text-sm font-medium text-gray-900">{category.count}</span>
                </div>
              </div>
            )) || (
              <p className="text-sm text-gray-500">Cargando categorías...</p>
            )}
          </div>
        </div>

        {/* Low Stock Alert */}
        <div className="bg-white rounded-lg shadow-md p-6 border border-gray-200">
          <div className="flex items-center justify-between mb-4">
            <h3 className="text-lg font-semibold text-gray-900">Stock Bajo</h3>
            <AlertTriangle className="h-5 w-5 text-red-500" />
          </div>
          <div className="space-y-3">
            {lowStockProducts.length === 0 ? (
              <p className="text-sm text-gray-500">No hay productos con stock bajo</p>
            ) : (
              lowStockProducts.slice(0, 5).map((product) => (
                <div key={product.id} className="flex items-center justify-between p-3 bg-red-50 rounded-lg">
                  <div>
                    <p className="font-medium text-sm text-gray-900">{product.name}</p>
                    <p className="text-xs text-gray-500">{product.code}</p>
                  </div>
                  <div className="text-right">
                    <p className="text-sm font-medium text-red-600">{product.currentStock}</p>
                    <p className="text-xs text-gray-500">Min: {product.minimumStock}</p>
                  </div>
                </div>
              ))
            )}
          </div>
        </div>

        {/* Recent Movements */}
        <div className="bg-white rounded-lg shadow-md p-6 border border-gray-200">
          <div className="flex items-center justify-between mb-4">
            <h3 className="text-lg font-semibold text-gray-900">Movimientos Recientes</h3>
            <Activity className="h-5 w-5 text-gray-500" />
          </div>
          <div className="space-y-3">
            {recentMovements.length === 0 ? (
              <p className="text-sm text-gray-500">No hay movimientos recientes</p>
            ) : (
              recentMovements.map((movement) => (
                <div key={movement.id} className="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                  <div>
                    <p className="font-medium text-sm text-gray-900">{movement.productName}</p>
                    <p className="text-xs text-gray-500">
                      {movement.type === 'entry' ? 'Entrada' : 
                       movement.type === 'positive_adjustment' ? 'Ajuste +' :
                       movement.type === 'negative_adjustment' ? 'Ajuste -' : 'Devolución'}
                    </p>
                  </div>
                  <div className="text-right">
                    <p className={`text-sm font-medium ${
                      movement.quantity > 0 ? 'text-green-600' : 'text-red-600'
                    }`}>
                      {movement.quantity > 0 ? '+' : ''}{movement.quantity}
                    </p>
                    <p className="text-xs text-gray-500">
                      {new Date(movement.date).toLocaleDateString()}
                    </p>
                  </div>
                </div>
              ))
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;