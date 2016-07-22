Rails.application.routes.draw do

  #Problems
  get '/problems', to: 'problems#index'
  get '/problems/:id', to: 'problems#show'
  post '/problems', to: 'problems#create'

  #Users
  post '/users/login', to: 'users#login'
  post '/users/register', to: 'users#register'

end
