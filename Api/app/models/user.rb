class User < ApplicationRecord
  has_many :problems
  validates :name, :presence => true
  validates :email, :presence => true
  validates :password, :presence => true
  validates :phone, :presence => true
end
