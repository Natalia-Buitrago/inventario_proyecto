import React, { useState } from 'react';
import Layout from './components/Layout';
import Dashboard from './components/Dashboard';
import ProductForm from './components/ProductForm';
import StockControl from './components/StockControl';
import InventoryMovements from './components/InventoryMovements';
import Reports from './components/Reports';

function App() {
  const [currentView, setCurrentView] = useState('dashboard');

  const renderCurrentView = () => {
    switch (currentView) {
      case 'dashboard':
        return <Dashboard />;
      case 'products':
        return <ProductForm />;
      case 'stock':
        return <StockControl />;
      case 'movements':
        return <InventoryMovements />;
      case 'reports':
        return <Reports />;
      default:
        return <Dashboard />;
    }
  };

  return (
    <Layout currentView={currentView} onViewChange={setCurrentView}>
      {renderCurrentView()}
    </Layout>
  );
}

export default App;