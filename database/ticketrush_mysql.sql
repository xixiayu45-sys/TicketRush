CREATE DATABASE IF NOT EXISTS ticketrush
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE ticketrush;

CREATE TABLE IF NOT EXISTS users (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  username VARCHAR(32) NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  UNIQUE KEY uk_users_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS events (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(120) NOT NULL,
  description TEXT NULL,
  total_stock INT UNSIGNED NOT NULL,
  available_stock INT UNSIGNED NOT NULL,
  start_time DATETIME(3) NOT NULL,
  end_time DATETIME(3) NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  KEY idx_events_start_time (start_time),
  KEY idx_events_end_time (end_time),
  CONSTRAINT ck_events_total_stock_positive CHECK (total_stock > 0),
  CONSTRAINT ck_events_available_stock_range CHECK (available_stock <= total_stock),
  CONSTRAINT ck_events_time_range CHECK (end_time > start_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS ticket_orders (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  order_no VARCHAR(64) NOT NULL,
  user_id BIGINT UNSIGNED NOT NULL,
  event_id BIGINT UNSIGNED NOT NULL,
  quantity INT UNSIGNED NOT NULL,
  status ENUM('PENDING', 'PAID', 'CANCELLED', 'EXPIRED', 'REFUNDED') NOT NULL DEFAULT 'PENDING',
  total_amount DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  paid_at DATETIME(3) NULL,
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  UNIQUE KEY uk_ticket_orders_order_no (order_no),
  KEY idx_ticket_orders_user_id (user_id),
  KEY idx_ticket_orders_event_status (event_id, status),
  CONSTRAINT fk_ticket_orders_user_id FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_ticket_orders_event_id FOREIGN KEY (event_id) REFERENCES events (id),
  CONSTRAINT ck_ticket_orders_quantity_positive CHECK (quantity > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS stock_logs (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  event_id BIGINT UNSIGNED NOT NULL,
  order_id BIGINT UNSIGNED NULL,
  change_amount INT NOT NULL,
  before_available_stock INT UNSIGNED NOT NULL,
  after_available_stock INT UNSIGNED NOT NULL,
  reason ENUM('CREATE_EVENT', 'LOCK_STOCK', 'RELEASE_STOCK', 'CONFIRM_ORDER', 'ADMIN_ADJUST') NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  KEY idx_stock_logs_event_id (event_id),
  KEY idx_stock_logs_order_id (order_id),
  CONSTRAINT fk_stock_logs_event_id FOREIGN KEY (event_id) REFERENCES events (id),
  CONSTRAINT fk_stock_logs_order_id FOREIGN KEY (order_id) REFERENCES ticket_orders (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
