import React from 'react';
import { AuthProvider } from './context/AuthContext';
import { BasketProvider } from './context/BasketContext';
import { Navbar } from './components/Navbar';
import { ProtectedRoute } from './components/RouteGuards';

// Global Authentication & Registration Pages
import { Login } from './pages/Login';
import { Register } from './pages/Register';

// Client Storefront Pages
import { ProductPage } from './pages/client/ProductPage';
import { SingleProductPage } from './pages/client/SingleProductPage';
import { Basket } from './pages/client/Basket';
import { OrderCheckout } from './pages/client/OrderCheckout';
import { MyOrders } from './pages/client/MyOrders';
import { OrderDetails } from './pages/client/OrderDetails';
import { CreateSubscription } from './pages/client/CreateSubscription';
import { MySubscriptions } from './pages/client/MySubscriptions';

// Admin  pages
import { AdminWarehouse } from './pages/admin/AdminWarehouse';
import { AdminCatalog } from './pages/admin/AdminCatalog';
import { AdminPlan } from './pages/admin/AdminPlan';
import { AdminOrders } from './pages/admin/AdminOrders';
import { AdminDeliveries } from './pages/admin/AdminDeliveries';
import { AdminAnalytics } from './pages/admin/AdminAnalytics';

export const App: React.FC = () => {
  const currentPath = window.location.pathname;

  const renderPage = () => {
    // Exact structural matching for vanilla routing
    switch (currentPath) {
      /* --- Auth Routes --- */
      case '/login':
        return <Login />;
      case '/register':
        return <Register />;

      /* --- Shopping & Checkout Routes --- */
      case '/korpa':
        return (
          <ProtectedRoute>
            <Basket />
          </ProtectedRoute>
        );
      case '/checkout':
        return (
          <ProtectedRoute>
            <OrderCheckout />
          </ProtectedRoute>
        );

      /* --- Order Tracking Routes --- */
      case '/porudzbine':
        return (
          <ProtectedRoute>
            <MyOrders />
          </ProtectedRoute>
        );

      /* --- Subscription Management Routes --- */
      case '/pretplate':
        return (
          <ProtectedRoute>
            <MySubscriptions />
          </ProtectedRoute>
        );
      case '/pretplate/nova':
        return (
          <ProtectedRoute>
            <CreateSubscription />
          </ProtectedRoute>
        );

      /* --- Admin Routes --- */
      case '/admin/magacin':
        return (
          <ProtectedRoute>
            <AdminWarehouse />
          </ProtectedRoute>
        );
      case '/admin/katalog':
        return (
          <ProtectedRoute>
            <AdminCatalog />
          </ProtectedRoute>
        );
      case '/admin/plan':
        return (
          <ProtectedRoute >
            <AdminPlan />
          </ProtectedRoute>
        );
      case '/admin/porudzbine':
        return (
          <ProtectedRoute>
            <AdminOrders />
          </ProtectedRoute>
        );
      case '/admin/dostave':
        return (
          <ProtectedRoute>
            <AdminDeliveries />
          </ProtectedRoute>
        );
      case '/admin/analitika':
        return (
          <ProtectedRoute>
            <AdminAnalytics />
          </ProtectedRoute>
        );

      // ... dodaj ostale admin rute


      /* --- Dynamic Match Fallbacks --- */
      default:
        // Matches: /proizvod/{id}
        if (currentPath.match(/^\/proizvod\//)) {
          return <SingleProductPage />;
        }
        // Matches: /porudzbine/{id}
        if (currentPath.match(/^\/porudzbine\//)) {
          return (
            <ProtectedRoute>
              <OrderDetails />
            </ProtectedRoute>
          );
        }
        // Main Storefront landing catalog
        return <ProductPage />;
    }
  };

  return (
    <AuthProvider>
      <BasketProvider>
        <Navbar />
        {renderPage()}
      </BasketProvider>
    </AuthProvider>
  );
};

export default App;