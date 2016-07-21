class CreateProblems < ActiveRecord::Migration[5.0]
  def change
    create_table :problems do |t|
      t.string :title
      t.text :description
      t.string :coordinates
      t.string :imageUri
      t.datetime :timestamp

      t.timestamps
    end
  end
end
