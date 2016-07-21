Rails.application.routes.draw do

  devise_for :users

  #Problems
  get '/problems', to: 'problems#index'
  get '/problems/:id', to: 'problems#show'
  post '/problems', to: 'problems#create'

end
